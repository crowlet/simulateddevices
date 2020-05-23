package pl.wat.wcy.panek.simulateddevices.application;

import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.infrastructure.HealthDataProvider;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EcgDataService extends DataService {

    private final HealthDataProvider healthDataProvider;
    private final Set<String> users;

    public EcgDataService(MessagePublisher messagePublisher, HealthDataProvider healthDataProvider) {
        super(messagePublisher);
        this.healthDataProvider = healthDataProvider;
        this.users = new HashSet<>();
    }

    @Override
    protected List<FileEntry> data(String userId) {
        return healthDataProvider.ecgData(userId);
    }

    @Override
    protected Message message(FileEntry val, String userId) {
        this.users.add(userId);
        return new EcgMessage(val.getValue(), LocalDateTime.now(), userId);
    }
}
