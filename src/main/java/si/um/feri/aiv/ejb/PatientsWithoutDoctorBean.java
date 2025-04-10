package si.um.feri.aiv.ejb;

import si.um.feri.aiv.dao.*;
import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import si.um.feri.aiv.vao.Patient;
import java.util.ArrayList;

@Stateless
public class PatientsWithoutDoctorBean implements PatientsWithoutDoctorRemote {

    @EJB
    private PatientDAO patientDAO;

    @Override
    public ArrayList<Patient> getPatientsWithoutDoctor() {
        return patientDAO.getAllPatientsWithoutDoctor();
    }
}
