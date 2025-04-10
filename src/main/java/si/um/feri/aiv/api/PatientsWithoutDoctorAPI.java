package si.um.feri.aiv.api;

import si.um.feri.aiv.ejb.PatientsWithoutDoctorRemote;
import si.um.feri.aiv.vao.Patient;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/api/v1/patientsWithoutDoctor")
public class PatientsWithoutDoctorAPI extends HttpServlet {

    @EJB
    private PatientsWithoutDoctorRemote patientsWithoutDoctorBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Patient> patients = patientsWithoutDoctorBean.getPatientsWithoutDoctor();

        response.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print("[");
            for (int i = 0; i < patients.size(); i++) {
                Patient p = patients.get(i);
                out.print("{");
                out.print("\"firstName\":\"" + p.getFirstName() + "\",");
                out.print("\"surname\":\"" + p.getSurname() + "\",");
                out.print("\"email\":\"" + p.getEmail() + "\",");
                out.print("\"dateOfBirth\":\"" + p.getDateOfBirthString() + "\",");
                out.print("\"details\":\"" + p.getDetails() + "\",");
                out.print("\"doctor\":\"" + (p.getDoctor() != null ? p.getDoctor() : "") + "\"");
                out.print("}");
                if (i < patients.size() - 1) out.print(",");
            }
            out.print("]");
        }
    }
}

