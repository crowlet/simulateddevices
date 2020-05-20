package pl.wat.wcy.panek.simulateddevices.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileEntry {
    private double start;
    private double end;
    private float value;
}
