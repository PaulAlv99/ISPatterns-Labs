package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Patient;
import java.util.ArrayList;
public interface PatientDAO {

    // Retrieve all patients
    ArrayList<Patient> getAllPatients();

    ArrayList<Patient> getAllPatientsWithDoctor();

    ArrayList<Patient> getAllPatientsWithoutDoctor();

    // Retrieve a patient by ID
    Patient getPatientById(int id);

    ArrayList<Patient> getPatientByName(String firstName, String surname);

    ArrayList<Patient> getPatientByEmail(String email);

    // Create a new patient record
    void addPatient(Patient patient);

    // Update an existing patient record
    void updatePatient(Patient patient);

    // Delete a patient record by ID
    void deletePatient(int id);
}
