package si.um.feri.aiv.jsf;

import jakarta.ejb.EJB;
import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.ejb.PatientsWithoutDoctorRemote;
import si.um.feri.aiv.vao.Patient;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@Named("patient")
@SessionScoped
public class PatientJSFBEAN implements Serializable {
    private Patient patient = new Patient();
    @EJB
    private PatientDAO patientDAO;

    @EJB
    private PatientsWithoutDoctorRemote patientsWithoutDoctorEJB;

    public ArrayList<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public ArrayList<Patient> getAllPatientsWithDoctor(){
        return patientDAO.getAllPatientsWithDoctor();
    }

    public ArrayList<Patient> getAllPatientsWithoutDoctor(){
        return patientDAO.getAllPatientsWithoutDoctor();
    }

    public ArrayList<Patient> getPatientsWithoutDoctorRemote(){
        return patientsWithoutDoctorEJB.getPatientsWithoutDoctor();
    }
    public Patient getPatient() {
        return patient;
    }

    public void addPatient() {
        if (patient.getAction() == Patient.action.EDIT) {
            // Edit existing patient (you might need to update the DAO)
            patientDAO.updatePatient(patient);
            System.out.println("Edited: " + patient);
        } else {
            // Add new patient
            patientDAO.addPatient(new Patient(patient.getFirstName(), patient.getSurname(),
                    patient.getEmail(), patient.getDateOfBirth(), patient.getDetails(), patient.getDoctor()));
            System.out.println("Added: " + patient);
        }
        // Reset form after saving
        patient = new Patient();
    }


    public void editPatient(Patient p) {
        this.patient = p;
        this.patient.setAction(Patient.action.EDIT);
    }

    public void deletePatient(Patient p) {
        patientDAO.deletePatient(getAllPatients().indexOf(p));
    }
}
