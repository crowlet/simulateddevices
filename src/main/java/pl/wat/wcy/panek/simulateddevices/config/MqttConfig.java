package pl.wat.wcy.panek.simulateddevices.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Bean
    IMqttClient mqttClient(
            @Value("${application.mqtt.protocol}://${application.mqtt.host}:${application.mqtt.port}")
            final String mqttBrokerUrl,
            final PublisherIdProvider publisherIdProvider,
            final MqttConnectOptions options
    ) throws MqttException {

        var mqttClient = new MqttClient(mqttBrokerUrl, publisherIdProvider.id());
        mqttClient.connect(options);
        return mqttClient;
    }

    @Bean
    MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }
}
