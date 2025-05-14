package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Patient;
import si.um.feri.aiv.vao.Doctor;
import jakarta.ejb.Stateless;
import jakarta.ejb.Local;
import jakarta.persistence.*;
import java.util.List;

@Stateless
@Local(PatientDAO.class)
public class PatientDAOBean implements PatientDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Patient> getAllPatients() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    @Override
    public List<Patient> getAllPatientsWithDoctor() {
        return em.createQuery("SELECT p FROM Patient p WHERE p.doctor IS NOT NULL", Patient.class)
                .getResultList();
    }

    @Override
    public List<Patient> getAllPatientsWithoutDoctor() {
        return em.createQuery("SELECT p FROM Patient p WHERE p.doctor IS NULL", Patient.class)
                .getResultList();
    }

    @Override
    public Patient getPatientById(int id) {
        return em.find(Patient.class, id);
    }

    @Override
    public List<Patient> getPatientByName(String firstName, String surname) {
        return em.createQuery("SELECT p FROM Patient p WHERE LOWER(p.firstName) = LOWER(:firstName) AND LOWER(p.surname) = LOWER(:surname)", Patient.class)
                .setParameter("firstName", firstName)
                .setParameter("surname", surname)
                .getResultList();
    }

    @Override
    public List<Patient> getPatientByEmail(String email) {
        return em.createQuery("SELECT p FROM Patient p WHERE LOWER(p.email) = LOWER(:email)", Patient.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public void addPatient(Patient patient) {
        em.persist(patient);
    }

    @Override
    public void updatePatient(Patient patient) {
        em.merge(patient);
    }

    @Override
    public void deletePatient(int id) {
        Patient p = em.find(Patient.class, id);
        if (p != null) {
            Doctor doctor = p.getDoctor();
            if (doctor != null) {
                p.setDoctor(null);
            }
            em.remove(p);
        }
    }
}
