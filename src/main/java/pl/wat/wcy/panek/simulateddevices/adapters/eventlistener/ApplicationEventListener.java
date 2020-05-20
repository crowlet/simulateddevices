package pl.wat.wcy.panek.simulateddevices.adapters.eventlistener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.application.EcgDataService;
import pl.wat.wcy.panek.simulateddevices.application.SpO2DataService;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ApplicationEventListener {

    private final SpO2DataService spO2DataService;
    private final EcgDataService ecgDataService;
    private final AtomicBoolean done;
    private final Set<String> userIds;
    private final ExecutorService executors;

    public ApplicationEventListener(EcgDataService ecgDataService, SpO2DataService spO2DataService, @Value("${application.userIds}") Set<String> userIds) {
        this.spO2DataService = spO2DataService;
        this.ecgDataService = ecgDataService;
        this.done = new AtomicBoolean(false);
        this.userIds = userIds;
        this.executors = Executors.newCachedThreadPool();
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event){
        if(!done.get()) {
            done.set(true);
            generateSpO2();
            generateEcg();
        }
    }

    private void generateEcg() {
        userIds.forEach(val ->
                executors.submit(() -> ecgDataService.publish(val))
        );

    }

    private void generateSpO2() {
        userIds.forEach(val -> executors.submit(() -> spO2DataService.publish(val)));
    }


}
