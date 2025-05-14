package si.um.feri.aiv.jsf;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.jms.*;

import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.dao.DoctorDAO;
import si.um.feri.aiv.ejb.PatientsWithoutDoctorRemote;
import si.um.feri.aiv.jms.DoctorSelectionRequest;
import si.um.feri.aiv.vao.Patient;
import si.um.feri.aiv.vao.Doctor;

import java.io.Serializable;
import java.util.List;

@Named("patient")
@SessionScoped
public class PatientJSFBEAN implements Serializable {

    private Patient patient = new Patient();

    @EJB
    private PatientDAO patientDAO;

    @EJB
    private DoctorDAO doctorDAO;

    @EJB
    private PatientsWithoutDoctorRemote patientsWithoutDoctorEJB;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/doctorSelectionQueue")
    private Queue doctorSelectionQueue;

    private Integer selectedDoctorId;

    public Integer getSelectedDoctorId() {
        return selectedDoctorId;
    }

    public void setSelectedDoctorId(Integer selectedDoctorId) {
        this.selectedDoctorId = selectedDoctorId;
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public List<Patient> getAllPatientsWithDoctor() {
        return patientDAO.getAllPatientsWithDoctor();
    }

    public List<Patient> getAllPatientsWithoutDoctor() {
        return patientDAO.getAllPatientsWithoutDoctor();
    }

    public List<Patient> getPatientsWithoutDoctorRemote() {
        return patientsWithoutDoctorEJB.getPatientsWithoutDoctor();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void savePatient() {
        boolean isEdit = patient.getAction() == Patient.action.EDIT;

        if (isEdit) {
            patientDAO.updatePatient(patient);
        } else {
            patientDAO.addPatient(patient); // Important: persist first to get valid ID
        }

        //Producer
        if (selectedDoctorId != null) {
            try (JMSContext context = connectionFactory.createContext()) {
                DoctorSelectionRequest req = new DoctorSelectionRequest(
                        patient.getPatientId(),
                        selectedDoctorId,
                        patient.getEmail()
                );
                ObjectMessage msg = context.createObjectMessage(req);
                context.createProducer().send(doctorSelectionQueue, msg);

                System.out.println("[JMS] Sent DoctorSelectionRequest: " + req);
            } catch (Exception e) {
                System.err.println("[JMS] Failed to send message");
                e.printStackTrace();
            }
        }

        // Reset form
        patient = new Patient();
        selectedDoctorId = null;
    }

    public List<Doctor> getEligibleDoctors() {
        List<Doctor> eligible = doctorDAO.getEligibleDoctors();

        Doctor current = patient.getDoctor();
        if (current != null && eligible.stream().noneMatch(d -> d.getDoctorId() == current.getDoctorId())) {
            eligible.add(doctorDAO.getDoctor(current.getDoctorId()));
        }

        return eligible;
    }

    public void editPatient(Patient p) {
        this.patient = p;
        this.patient.setAction(Patient.action.EDIT);
    }

    public void deletePatient(Patient p) {
        patientDAO.deletePatient(p.getPatientId());
    }

    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }
}
