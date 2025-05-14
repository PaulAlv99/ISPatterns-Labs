package si.um.feri.aiv.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import si.um.feri.aiv.dao.DoctorDAO;
import si.um.feri.aiv.dao.DoctorSelectionRequestDAO;
import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.DoctorSelectionRequestEntity;
import si.um.feri.aiv.vao.Patient;

import java.io.Serializable;
import java.util.List;

@Named("doctor")
@SessionScoped
public class DoctorJSFBEAN implements Serializable {

    private Doctor selectedDoctor = new Doctor();
    private List<Patient> selectedDoctorPatients;

    @EJB
    private DoctorDAO doctorDAO;

    @EJB
    DoctorSelectionRequestDAO requestDAO;

    @EJB
    PatientDAO patientDAO;

    public List<DoctorSelectionRequestEntity> getPendingRequests() {
        if (selectedDoctor != null)
            return requestDAO.getPendingRequestsForDoctor(selectedDoctor.getDoctorId());
        return List.of();
    }

    public String acceptRequest(int requestId) {
        DoctorSelectionRequestEntity req = requestDAO.getById(requestId);

        if (req == null || req.isHandled()) {
            return "doctorRequests.xhtml";
        }

        Doctor doc = doctorDAO.getDoctor(req.getDoctorId());
        Patient pat = patientDAO.getPatientByEmail(req.getPatientEmail()).stream().findFirst().orElse(null);

        if (doc != null && pat != null) {
            int currentCount = doctorDAO.getCurrentPatients(doc.getDoctorId());

            if (currentCount < doc.getMaxPatients()) {
                doc.setCurrentPatients(currentCount + 1);
                pat.setDoctor(doc);
                doctorDAO.updateDoctor(doc);
                patientDAO.updatePatient(pat);
                req.setAccepted(true);

                printEmail(pat.getEmail(), "Doctor request accepted",
                        "Your request was approved. You are now assigned to doctor: " + doc.getFirstName() + " " + doc.getSurname());
            } else {
                req.setAccepted(false);
                printEmail(pat.getEmail(), "Doctor request rejected",
                        "Your request was rejected because the doctor cannot take more patients.");
            }

            req.setHandled(true);
            requestDAO.update(req);
        }

        return "doctorRequests.xhtml";
    }

    public String rejectRequest(int requestId) {
        DoctorSelectionRequestEntity req = requestDAO.getById(requestId);

        if (req == null || req.isHandled()) {
            return "doctorRequests.xhtml";
        }

        req.setAccepted(false);
        req.setHandled(true);
        requestDAO.update(req);

        // Notify patient
        Patient pat = patientDAO.getPatientByEmail(req.getPatientEmail()).stream().findFirst().orElse(null);
        if (pat != null) {
            printEmail(pat.getEmail(), "Doctor request rejected",
                    "Your doctor selection request was rejected.");
        }

        return "doctorRequests.xhtml";
    }

    private void printEmail(String to, String subject, String text) {
        System.out.println("-------- Simulated Email --------");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + text);
        System.out.println("---------------------------------");
    }

    public String goToRequests() {
        return "doctorRequests.xhtml";
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            int count = doctorDAO.getCurrentPatients(d.getDoctorId());
            d.setCurrentPatients(count);
        }
        return doctors;
    }

    public Doctor getSelectedDoctor() {
        return selectedDoctor;
    }

    public void setSelectedDoctor(Doctor selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }

    public List<Patient> getSelectedDoctorPatients() {
        return selectedDoctorPatients;
    }

    public String viewPatients(Doctor doc) {
        this.selectedDoctor = doc;
        this.selectedDoctorPatients = doctorDAO.findPatientsByDoctorId(doc.getDoctorId());
        this.selectedDoctor = new Doctor();
        return "doctorPatients.xhtml";
    }

    public String addDoctor() {
        if (selectedDoctor.getAction() == Doctor.Action.EDIT) {
            selectedDoctor.setAction(Doctor.Action.FINISHED);
            doctorDAO.updateDoctor(selectedDoctor);
        } else {
            doctorDAO.addDoctor(selectedDoctor);
        }
        selectedDoctor = new Doctor();
        return "doctorPage.xhtml";
    }

    public void editDoctor(Doctor d) {
        this.selectedDoctor = d;
        this.selectedDoctor.setAction(Doctor.Action.EDIT);
    }

    public void deleteDoctor(Doctor d) {
        doctorDAO.deleteDoctor(d.getDoctorId());
    }
}
