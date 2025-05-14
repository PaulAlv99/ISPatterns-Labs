package si.um.feri.aiv.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import si.um.feri.aiv.vao.DoctorSelectionRequestEntity;

import java.util.List;

@Stateless
public class DoctorSelectionRequestDAO {

    @PersistenceContext
    private EntityManager em;

    public void addRequest(DoctorSelectionRequestEntity req) {
        em.persist(req);
    }

    public List<DoctorSelectionRequestEntity> getPendingRequestsForDoctor(int doctorId) {
        return em.createQuery(
                        "SELECT r FROM DoctorSelectionRequestEntity r WHERE r.doctorId = :docId AND r.handled = false",
                        DoctorSelectionRequestEntity.class)
                .setParameter("docId", doctorId)
                .getResultList();
    }

    public DoctorSelectionRequestEntity getById(int id) {
        return em.find(DoctorSelectionRequestEntity.class, id);
    }

    public void update(DoctorSelectionRequestEntity req) {
        em.merge(req);
    }
}
