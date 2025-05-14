package si.um.feri.aiv.vao;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {

    public enum Action {
        EDIT,
        FINISHED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;

    private String firstName;
    private String surname;
    private String email;
    private int maxPatients;
    @Transient
    private int currentPatients;

    @Enumerated(EnumType.STRING)
    private Action action = Action.FINISHED;

    @JsonbTransient
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Patient> patients;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getDoctorId() { return doctorId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getMaxPatients() { return maxPatients; }

    public void setMaxPatients(int maxPatients) { this.maxPatients = maxPatients; }

    public int getCurrentPatients() { return currentPatients; }

    public void setCurrentPatients(int currentPatients) { this.currentPatients = currentPatients; }

    @Override
    public String toString() {
        return firstName + " " + surname;
    }
}