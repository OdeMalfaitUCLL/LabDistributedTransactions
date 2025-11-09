package be.ucll.da.appointmentservice.adapters.messaging;

import be.ucll.da.appointmentservice.client.patient.api.model.ValidatePatientCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqMessageSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitMqMessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendValidatePatientCommand(Integer appointmentId, Integer patientId) {
        var command = new ValidatePatientCommand();
        command.patientId(patientId);
        command.appointmentId(appointmentId);
        sendToQueue("q.patient-service.validate-patient", command);
    }



    private void sendToQueue(String queue, Object message) {
        LOGGER.info("Sending message: " + message);

        this.rabbitTemplate.convertAndSend(queue, message);
    }
}
