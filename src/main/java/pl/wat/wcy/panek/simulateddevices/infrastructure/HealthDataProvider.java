package pl.wat.wcy.panek.simulateddevices.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.simulateddevices.application.FileEntry;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HealthDataProvider {

    @Value("${application.data.spo2}")
    private String spo2FilePath;
    @Value("${application.data.ecg}")
    private String ecgFilePath;

    public List<FileEntry> spO2Data() {
        try(Stream<String> stream = Files.lines(Path.of(getClass().getClassLoader().getResource(spo2FilePath).toURI()))) {
            return stream.map(this::toEntry).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    public List<FileEntry> ecgData() {
        try(Stream<String> stream = Files.lines(Path.of(getClass().getClassLoader().getResource(ecgFilePath).toURI()))) {
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
