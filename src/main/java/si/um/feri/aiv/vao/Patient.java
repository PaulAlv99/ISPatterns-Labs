package si.um.feri.aiv.vao;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Serializable {
    private static int patientId = 1;
    public enum action {
        EDIT,
        FINISHED
    }
    private action action;
    private String firstName;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private String details;
    private String doctor;

    // Constructor
    public Patient(String firstName, String surname, String email, LocalDate dateOfBirth, String details, String doctor) {
        this.action = action.FINISHED;
        this.patientId = patientId++;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
        this.doctor = doctor;
    }

    public Patient(String firstName, String surname, String email, LocalDate dateOfBirth, String details) {
        this.action = action.FINISHED;
        this.patientId = patientId++;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public Patient(){};
    // Getters and Setters

    public action getAction() {
        return action;
    }

    public void setAction(action action) {
        this.action = action;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthString() {
        return dateOfBirth.toString();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", details='" + details + '\'' +
                ", doctor='" + doctor + '\'' +
                '}';
    }
}
