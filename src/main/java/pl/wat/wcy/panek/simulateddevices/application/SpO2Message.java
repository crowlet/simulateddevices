package pl.wat.wcy.panek.simulateddevices.application;

import java.time.LocalDateTime;

public class SpO2Message extends Message {

    public SpO2Message(float value, LocalDateTime timestamp, String userId) {
        super(SpO2Message.class.getSimpleName(), value, timestamp, userId);
    }
}
