package si.um.feri.aiv.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import si.um.feri.aiv.dao.DoctorDAO;
import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.vao.Patient;
import si.um.feri.aiv.vao.Doctor;

import java.time.LocalDate;
import java.util.List;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientRestResource {

    @EJB
    private PatientDAO patientDAO;

    @EJB
    private DoctorDAO doctorDAO;

    @GET
    @Path("/all-patients")
    public List<Patient> getAllPatients() {
        System.out.println(patientDAO.getAllPatients());
        return patientDAO.getAllPatients();
    }

    @POST
    @Path("/add-patient")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPatient(Patient patient) {
        try {
            if (patient.getFirstName() == null || patient.getSurname() == null || patient.getEmail() == null) {
                return "{\"error\": \"Missing required fields\"}";
            }

            if (patient.getDoctor() != null && patient.getDoctor().getDoctorId() > 0) {
                Doctor doctor = doctorDAO.getDoctor(patient.getDoctor().getDoctorId());
                if (doctor == null) {
                    return "{\"error\": \"Doctor not found\"}";
                }
                patient.setDoctor(doctor);
                doctor.setCurrentPatients(doctor.getCurrentPatients() + 1);
                doctorDAO.updateDoctor(doctor);
            }

            patientDAO.addPatient(patient);
            return "{\"message\": \"Patient added successfully\"}";

        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    @PUT
    @Path("/{id}/assign-doctor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String assignDoctorToPatient(@PathParam("id") int patientId, int doctorId) {
        Doctor doctor = doctorDAO.getDoctor(doctorId);
        try {
            if (doctor == null || doctor.getDoctorId() <= 0) {
                return "{\"error\": \"Invalid doctor input.\"}";
            }

            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                return "{\"error\": \"Patient with ID " + patientId + " not found.\"}";
            }

            Doctor foundDoctor = doctorDAO.getDoctor(doctor.getDoctorId());
            if (foundDoctor == null) {
                return "{\"error\": \"Doctor with ID " + doctor.getDoctorId() + " not found.\"}";
            }

            if (foundDoctor.getCurrentPatients() >= foundDoctor.getMaxPatients()) {
                return "{\"error\": \"Doctor has reached max patient capacity.\"}";
            }

            patient.setDoctor(foundDoctor);
            foundDoctor.setCurrentPatients(foundDoctor.getCurrentPatients() + 1);

            doctorDAO.updateDoctor(foundDoctor);
            patientDAO.updatePatient(patient);

            return "{\"message\": \"Doctor assigned to patient successfully.\"}";

        } catch (Exception e) {
            return "{\"error\": \"Unexpected error: " + e.getMessage() + "\"}";
        }
    }

    @GET
    @Path("/without-doctor")
    public List<Patient> getPatientsWithoutDoctor() {
        return patientDAO.getAllPatientsWithoutDoctor();
    }

    @GET
    @Path("/with-doctor")
    public List<Patient> getPatientsWithDoctor() {
        return patientDAO.getAllPatientsWithDoctor();
    }

    @GET
    @Path("/eligible-doctors")
    public List<Doctor> getEligibleDoctors() {
        return doctorDAO.getEligibleDoctors();
    }

}
