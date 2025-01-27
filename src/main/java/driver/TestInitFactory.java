package driver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TestInitFactory {
    private static final Map<String, Supplier<TestInit>> mapper = new HashMap<>();

    public static final String CHROME_PLATFORM = "chrome";
    static {
        mapper.put(CHROME_PLATFORM, TestInitChrome::new);
    }

    public static TestInit chooseEnvironment(String platform) {
        return mapper.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(platform))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid Platform; " +
                        "must be one of the following: "
                        + String.join(" / ", mapper.keySet())))
                .getValue().get();
    }
}
