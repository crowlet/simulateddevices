package pl.wat.wcy.panek.simulateddevices.application;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.infrastructure.HealthDataProvider;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SpO2DataService extends DataService {

    private final HealthDataProvider healthDataProvider;
    private final Set<String> users;

    public SpO2DataService(MessagePublisher messagePublisher, HealthDataProvider healthDataProvider) {
        super(messagePublisher);
        this.healthDataProvider = healthDataProvider;
        users = new HashSet<>();
    }

    @Override
    protected Message message(FileEntry val, String userId) {
        this.users.add(userId);
        return new SpO2Message(val.getValue(), LocalDateTime.now(), userId);
    }

    @Override
    protected List<FileEntry> data(String userId) {
        return healthDataProvider.spO2Data(userId);
    }
}
