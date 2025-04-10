package si.um.feri.aiv.srvlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import si.um.feri.aiv.ejb.PatientsWithoutDoctorRemote;
import si.um.feri.aiv.vao.Patient;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet("/patientsWithoutDoctorRemote")
public class PatientsWithoutDoctorServlet extends HttpServlet {

    @EJB
    private PatientsWithoutDoctorRemote patientsWithoutDoctorBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Patient> patients = patientsWithoutDoctorBean.getPatientsWithoutDoctor();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Patients Without Doctor-Srvlet</title></head><body>");
            out.println("<h1>Patients Without Doctor</h1>");
            out.println("<a href='faces/mainPage.xhtml'>Go Back</a><br/><br/>");

            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>Email</th><th>Date of Birth</th><th>Details</th><th>Doctor</th></tr>");

            for (Patient p : patients) {
                out.println("<tr>");
                out.println("<td>" + p.getFirstName() + " " + p.getSurname() + "</td>");
                out.println("<td>" + p.getEmail() + "</td>");
                out.println("<td>" + p.getDateOfBirthString() + "</td>");
                out.println("<td>" + p.getDetails() + "</td>");
                out.println("<td>" + (p.getDoctor() != null ? p.getDoctor() : "None") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("</body></html>");
        }
    }
}

