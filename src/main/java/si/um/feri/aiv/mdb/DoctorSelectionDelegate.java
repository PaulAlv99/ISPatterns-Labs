package si.um.feri.aiv.mdb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import si.um.feri.aiv.dao.DoctorDAO;
import si.um.feri.aiv.dao.DoctorSelectionRequestDAO;
import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.DoctorSelectionRequestEntity;
import si.um.feri.aiv.vao.Patient;

@Stateless
public class DoctorSelectionDelegate {

    @EJB
    DoctorSelectionRequestDAO requestDAO;

    @EJB
    PatientDAO patientDAO;

    @EJB
    DoctorDAO doctorDAO;

    public String processSelection(int patientId, int doctorId) {
        Doctor doctor = doctorDAO.getDoctorById(doctorId);
        Patient patient = patientDAO.getPatientById(patientId);

        if (doctor == null || patient == null) return "invalid";

        DoctorSelectionRequestEntity request = new DoctorSelectionRequestEntity(patient.getEmail(), patientId, doctorId);
        requestDAO.addRequest(request);

        return "pending";
    }

    public Doctor getDoctorById(int doctorId) {
        return doctorDAO.getDoctorById(doctorId);
    }

    public Patient getPatientById(int patientId) {
        return patientDAO.getPatientById(patientId);
    }

    public void sendNotification(String to, String subject, String body) {
        System.out.println("-------- Simulated Email --------");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("---------------------------------");
    }

    public void notifyDoctorAndPatientOnRequest(Doctor doctor, Patient patient) {
        sendNotification(patient.getEmail(), "Doctor selection pending",
                "Your request has been sent and is awaiting approval from the doctor.");
        sendNotification(doctor.getEmail(), "New patient selection request",
                "You have received a new patient request. Please log in to approve or reject it.");
    }

    public void notifyPatientOnDecision(Patient patient, boolean accepted) {
        if (accepted) {
            sendNotification(patient.getEmail(), "Doctor selection accepted",
                    "You have been successfully assigned to the selected doctor.");
        } else {
            sendNotification(patient.getEmail(), "Doctor selection rejected",
                    "The doctor could not accept your request at this time.");
        }
    }
}
