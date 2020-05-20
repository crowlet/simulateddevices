package pl.wat.wcy.panek.simulateddevices.application;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Message {
    protected final String type;
    protected final float value;
    protected final LocalDateTime timestamp;
    protected final String userId;

    public Message(String type, float value, LocalDateTime timestamp, String userId) {
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
        this.userId = userId;
    }
}
