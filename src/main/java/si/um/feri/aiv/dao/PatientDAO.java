package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Patient;
import java.util.List;
public interface PatientDAO {

    List<Patient> getAllPatients();
    List<Patient> getAllPatientsWithDoctor();
    List<Patient> getAllPatientsWithoutDoctor();
    Patient getPatientById(int id);
    List<Patient> getPatientByName(String firstName, String surname);
    List<Patient> getPatientByEmail(String email);
    void addPatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatient(int id);
}
