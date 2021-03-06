package pl.wat.wcy.panek.simulateddevices.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wat.wcy.panek.simulateddevices.application.Message;
import pl.wat.wcy.panek.simulateddevices.application.MessagePublisher;

@Component
@Slf4j
public class MqttPublisher implements MessagePublisher {

    private final IMqttClient mqttClient;
    private final ObjectMapper objectMapper;
    private final String topic;

    public MqttPublisher(
            IMqttClient mqttClient,
            ObjectMapper objectMapper,
            @Value("${application.mqtt.topic}") String topic
    ) {
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    @SneakyThrows
    @Override
    public synchronized void send(Message message) {
        log.info("Publishing message: " + message);
        mqttClient.publish(topic, mqttMessage(message));
    }

    @SneakyThrows
    private MqttMessage mqttMessage(Message message) {
        MqttMessage mqttMessage = new MqttMessage(objectMapper.writeValueAsBytes(message));
        mqttMessage.setRetained(true);
        mqttMessage.setQos(1);
        return mqttMessage;
    }
}
