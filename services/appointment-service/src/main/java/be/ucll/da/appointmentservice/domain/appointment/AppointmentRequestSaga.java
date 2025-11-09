package be.ucll.da.appointmentservice.domain.appointment;

import be.ucll.da.appointmentservice.adapters.messaging.RabbitMqMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRequestSaga {
    private final RabbitMqMessageSender eventSender;
    private final AppointmentRepository repository;

    @Autowired
    public AppointmentRequestSaga(RabbitMqMessageSender eventSender, AppointmentRepository repository) {
        this.eventSender = eventSender;
        this.repository = repository;
    }

    public void executeSaga(Appointment appointment) {
        appointment.validatingPatient();
        eventSender.sendValidatePatientCommand(appointment.getId(), appointment.getPatientId());
    }
}
