package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

public interface DoctorDAO {
    Doctor getDoctor(int id);
    List<Doctor> getEligibleDoctors();
    Boolean isEligibleDoctor(Doctor doctor);
    List<Patient> findPatientsByDoctorId(int id);
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(int id);
    void addDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    void deleteDoctor(int id);
    int getCurrentPatients(int doctorId);
}
