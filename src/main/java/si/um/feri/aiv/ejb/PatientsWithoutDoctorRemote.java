package si.um.feri.aiv.ejb;

import jakarta.ejb.Remote;
import si.um.feri.aiv.vao.Patient;
import java.util.ArrayList;

@Remote
public interface PatientsWithoutDoctorRemote {
    ArrayList<Patient> getPatientsWithoutDoctor();
}
