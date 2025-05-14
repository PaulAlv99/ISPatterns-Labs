package si.um.feri.aiv.ejb;

import si.um.feri.aiv.dao.*;
import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import si.um.feri.aiv.vao.Patient;
import java.util.List;

@Stateless
public class PatientsWithoutDoctorBean implements PatientsWithoutDoctorRemote {

    @EJB
    private PatientDAO patientDAO;

    @Override
    public List<Patient> getPatientsWithoutDoctor() {
        return patientDAO.getAllPatientsWithoutDoctor();
    }
}
