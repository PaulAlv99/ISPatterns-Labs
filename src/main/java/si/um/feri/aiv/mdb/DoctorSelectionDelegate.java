package si.um.feri.aiv.mdb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import si.um.feri.aiv.dao.DoctorDAO;
import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

@Stateless
public class DoctorSelection {

    @EJB
    DoctorDAO doctorDAO;

    @EJB
    PatientDAO patientDAO;

    public String processSelection(String patientEmail, int doctorId) {
        Doctor doctor = doctorDAO.getDoctorById(doctorId);
        Patient patient = patientDAO.getPatientByEmail(patientEmail).stream().findFirst().orElse(null);

        if (doctor == null || patient == null) return "invalid";

        if (doctor.getCurrentPatients() >= doctor.getMaxPatients()) {
            return "rejected";
        }

        doctor.setCurrentPatients(doctor.getCurrentPatients() + 1);
        doctorDAO.updateDoctor(doctor);

        patient.setDoctor(doctor);
        patientDAO.updatePatient(patient);

        return "accepted";
    }
}

