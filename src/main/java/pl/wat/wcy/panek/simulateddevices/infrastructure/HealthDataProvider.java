package pl.wat.wcy.panek.simulateddevices.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.application.FileEntry;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.data")
public class HealthDataProvider {

    private Map<String, String> spo2;
    private Map<String, String> ecg;

    public Map<String, String> getSpo2() {
        return spo2;
    }

    public Map<String, String> getEcg() {
        return ecg;
    }

    public void setSpo2(Map<String, String> spo2Mapping) {
        this.spo2 = spo2Mapping;
    }

    public void setEcg(Map<String, String> ecgMapping) {
        this.ecg = ecgMapping;
    }

    public synchronized List<FileEntry> spO2Data(String userId) {
        try(Stream<String> stream = Files.lines(Path.of(getClass().getClassLoader().getResource(spo2.get(userId)).toURI()))) {
            return stream.map(this::toEntry).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    public synchronized List<FileEntry> ecgData(String userId) {
        try(Stream<String> stream = Files.lines(Path.of(getClass().getClassLoader().getResource(ecg.get(userId)).toURI()))) {
            return stream.map(this::toEntry).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    private FileEntry toEntry(String s) {
        var data = s.split(";");
        return new FileEntry(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Float.parseFloat(data[2]));
    }
}
