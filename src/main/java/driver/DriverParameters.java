package driver;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DriverParameters {
    private String platform;
    private List<String> chromeArguments;
    private Boolean headless;
}
