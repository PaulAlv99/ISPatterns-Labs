package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Patient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PatientDAOImplementation implements PatientDAO {
    private ArrayList<Patient> patients = new ArrayList<>();

    @Override
    public ArrayList<Patient> getAllPatients() {
        return patients;
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
        // Update logic can be implemented
    }

    @Override
    public void deletePatient(int id) {
        if (id >= 0 && id < patients.size()) {
            patients.remove(id);
        }
    }
}
