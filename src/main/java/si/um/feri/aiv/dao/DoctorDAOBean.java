package si.um.feri.aiv.dao;

import jakarta.ejb.Stateless;
import jakarta.ejb.Local;
import jakarta.persistence.*;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

@Stateless
@Local(DoctorDAO.class)
public class DoctorDAOBean implements DoctorDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Doctor getDoctor(int id) {
        return em.find(Doctor.class, id);
    }

    @Override
    public List<Doctor> getEligibleDoctors() {
        return em.createQuery("SELECT d FROM Doctor d WHERE SIZE(d.patients) < d.maxPatients", Doctor.class)
                .getResultList();
    }

    @Override
    public Boolean isEligibleDoctor(Doctor doctor) {
        return doctor.getMaxPatients() != doctor.getCurrentPatients();
    }

    @Override
    public List<Patient> findPatientsByDoctorId(int doctorId) {
        return em.createQuery(
                        "SELECT p FROM Patient p WHERE p.doctor.doctorId = :id", Patient.class)
                .setParameter("id", doctorId)
                .getResultList();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    @Override
    public int getCurrentPatients(int doctorId) {
        Long count = em.createQuery(
                        "SELECT COUNT(p) FROM Patient p WHERE p.doctor.doctorId = :doctorId", Long.class)
                .setParameter("doctorId", doctorId)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public Doctor getDoctorById(int id) {
        return em.find(Doctor.class, id);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        em.persist(doctor);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        em.merge(doctor);
    }

    @Override
    public void deleteDoctor(int id) {
        Doctor d = em.find(Doctor.class, id);
        if (d != null) {
            em.remove(d);
        }
    }
}
