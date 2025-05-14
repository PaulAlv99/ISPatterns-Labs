package si.um.feri.aiv.vao;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "doctor_selection_requests")
public class DoctorSelectionRequestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String patientEmail;

    private int patientId;

    private int doctorId;

    private boolean handled = false;
    private Boolean accepted; // null = pending

    public DoctorSelectionRequestEntity() {}

    public DoctorSelectionRequestEntity(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
    public DoctorSelectionRequestEntity(String patientEmail,int patientId, int doctorId) {
        this.patientEmail = patientEmail;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
