package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Patient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.ArrayList;

@ApplicationScoped
public class PatientDAOImplementation implements PatientDAO {
    private ArrayList<Patient> patients = new ArrayList<>();

    @Override
    public ArrayList<Patient> getAllPatients() {
        return patients;
    }

    @Override
    public ArrayList<Patient> getAllPatientsWithDoctor() {
        ArrayList<Patient> patientsWithDoctor = new ArrayList<>();
        for(Patient patient : patients) {
            if(!patient.getDoctor().isEmpty()) {
                patientsWithDoctor.add(patient);
            }
        }
        return patientsWithDoctor;
    }
    @Override
    public ArrayList<Patient> getAllPatientsWithoutDoctor() {
        ArrayList<Patient> patientsWithoutDoctor = new ArrayList<>();
        for(Patient patient : patients) {
            if(patient.getDoctor().isEmpty()) {
                patientsWithoutDoctor.add(patient);
            }
        }
        return patientsWithoutDoctor;
    }
    @Override
    public Patient getPatientById(int id) {
        return (id >= 0 && id < patients.size()) ? patients.get(id) : null;
    }

    @Override
    public ArrayList<Patient> getPatientByName(String firstName, String surname) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getFirstName().equalsIgnoreCase(firstName) && p.getSurname().equalsIgnoreCase(surname)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Patient> getPatientByEmail(String email) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    @Override
    public void updatePatient(Patient patient) {

    }

    @Override
    public void deletePatient(int id) {
        if (id >= 0 && id < patients.size()) {
            patients.remove(id);
        }
    }
}
