package pl.wat.wcy.panek.simulateddevices.infrastructure;

import org.springframework.stereotype.Component;
import pl.wat.wcy.panek.simulateddevices.SimulateddevicesApplication;
import pl.wat.wcy.panek.simulateddevices.config.PublisherIdProvider;

@Component
public class ApplicationNameIdProvider implements PublisherIdProvider {

    @Override
    public String id() {
        return SimulateddevicesApplication.class.getSimpleName();
    }
}
