package si.um.feri.aiv.jsf;

import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.vao.Patient;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Named("patient")
@SessionScoped
public class PatientJSFBEAN implements Serializable {
    private static final long serialVersionUID = 1L;
    private Patient patient = new Patient();
    private ArrayList<Patient> patients = new ArrayList<>();
    @Inject
    private PatientDAO patientDAO;


    public ArrayList<Patient> getPatients() {
        return patientDAO.getAllPatients();
    }

    public Patient getPatient() {
        return patient;
    }

    public void addPatient() {
        patientDAO.addPatient(new Patient(patient.getFirstName(), patient.getSurname(), patient.getEmail(),
                patient.getDateOfBirth(), patient.getDetails(), patient.getDoctor()));
        System.out.println(patient);
        // Reset form after submission
        patient = new Patient();

    }

    public void editPatient(Patient p) {
        this.patient = p;
    }

    public void deletePatient(Patient p) {
        patientDAO.deletePatient(getPatients().indexOf(p));
    }
}
