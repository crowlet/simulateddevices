package pl.wat.wcy.panek.simulateddevices.application;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.infrastructure.HealthDataProvider;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpO2DataService extends DataService {

    private final HealthDataProvider healthDataProvider;

    public SpO2DataService(MessagePublisher messagePublisher, HealthDataProvider healthDataProvider) {
        super(messagePublisher);
        this.healthDataProvider = healthDataProvider;
    }

    @Override
    protected Message message(FileEntry val, String userId) {
        return new SpO2Message(val.getValue(), LocalDateTime.now(), userId);
    }

    @Override
    protected List<FileEntry> data() {
        return healthDataProvider.spO2Data();
    }
}
