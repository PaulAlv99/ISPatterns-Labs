package si.um.feri.aiv.jms;

import java.io.Serializable;

public class DoctorSelectionRequest implements Serializable {

    private int patientId;
    private int doctorId;
    private String patientEmail;

    public DoctorSelectionRequest(int patientId, int doctorId, String patientEmail) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientEmail = patientEmail;
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

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
