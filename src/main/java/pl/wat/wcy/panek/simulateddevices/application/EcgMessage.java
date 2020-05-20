package pl.wat.wcy.panek.simulateddevices.application;

import java.time.LocalDateTime;

public class EcgMessage extends Message {

    public EcgMessage(float value, LocalDateTime timestamp, String userId) {
        super(EcgMessage.class.getSimpleName(), value, timestamp, userId);
    }
}
