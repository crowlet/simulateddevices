package pl.wat.wcy.panek.simulateddevices.application;

import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.infrastructure.HealthDataProvider;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EcgDataService extends DataService {

    private final HealthDataProvider healthDataProvider;

    public EcgDataService(MessagePublisher messagePublisher, HealthDataProvider healthDataProvider) {
        super(messagePublisher);
        this.healthDataProvider = healthDataProvider;
    }

    @Override
    protected List<FileEntry> data() {
        return healthDataProvider.ecgData();
    }

    @Override
    protected Message message(FileEntry val, String userId) {
        return new EcgMessage(val.getValue(), LocalDateTime.now(), userId);
    }
}
