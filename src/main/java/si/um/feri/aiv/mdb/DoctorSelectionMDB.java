package si.um.feri.aiv.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import si.um.feri.aiv.jms.DoctorSelectionRequest;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/doctorSelectionQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class DoctorSelectionMDB implements MessageListener {

    @EJB
    private DoctorSelectionDelegate delegate;

    @Override
    public void onMessage(Message message) {
        try {
            if (!(message instanceof ObjectMessage objectMessage)) {
                System.out.println("[MDB] Skipping message: Not an ObjectMessage.");
                return;
            }

            Object obj = objectMessage.getObject();
            if (!(obj instanceof DoctorSelectionRequest request)) {
                System.out.println("[MDB] Invalid message payload: Not a DoctorSelectionRequest.");
                return;
            }

            System.out.printf("[MDB] Processing doctor selection request: Patient ID=%d, Doctor ID=%d%n",
                    request.getPatientId(), request.getDoctorId());

            String result = delegate.processSelection(request.getPatientId(), request.getDoctorId());

            Doctor doctor = delegate.getDoctorById(request.getDoctorId());
            Patient patient = delegate.getPatientById(request.getPatientId());

            switch (result) {
                case "pending" -> {
                    delegate.notifyDoctorAndPatientOnRequest(doctor, patient);
                    System.out.println("[MDB] Notified doctor and patient about pending request.");
                }
                case "accepted" -> {
                    delegate.notifyPatientOnDecision(patient, true);
                    System.out.println("[MDB] Doctor accepted request. Patient notified.");
                }
                case "rejected" -> {
                    delegate.notifyPatientOnDecision(patient, false);
                    System.out.println("[MDB] Doctor rejected request. Patient notified.");
                }
                case "invalid" -> {
                    System.out.printf("[MDB] Invalid doctor or patient (ID=%d, ID=%d)%n",
                            request.getPatientId(), request.getDoctorId());
                }
                default -> {
                    System.out.printf("[MDB] Unrecognized status '%s' in doctor selection process.%n", result);
                }
            }

        } catch (Exception e) {
            System.err.println("[MDB] Error while processing doctor selection request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
