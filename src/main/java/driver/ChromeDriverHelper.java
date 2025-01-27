package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Objects;


public class ChromeDriverHelper {
    private static final Logger LOGGER = Logger.getLogger(ChromeDriverHelper.class);

    public static void setupChromeDriver() {
        String chromeVersion = getChromeVersion();
        verifyOldChromeVersion(chromeVersion);
        WebDriverManager
                .chromedriver()
                .clearDriverCache()
                .browserVersion(chromeVersion)
                .setup();
    }

    public static String getChromeVersion() {
        String os = System.getProperty("os.name");
        LOGGER.info("OS: " + os);
        os = os.startsWith("Mac") ? "mac" : os;
        os = os.startsWith("Windows") ? "win" : os;
        os = os.contains("nux") ? "nux" : os;
        LOGGER.info("OS.type: " + os);
        String version = null;
        byte var4 = -1;
        switch (os.hashCode()) {
            case 107855:
                if (os.equals("mac")) {
                    var4 = 0;
                }
                break;
            case 109457:
                if (os.equals("nux")) {
                    var4 = 2;
                }
                break;
            case 117724:
                if (os.equals("win")) {
                    var4 = 1;
                }
        }

        ProcessBuilder pb;
        String output;
        switch (var4) {
            case 0:
                pb = new ProcessBuilder(new String[]{"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "--version"});
                output = null;

                try {
                    output = readOutput(pb);
                    version = ((String) Objects.requireNonNull(output)).split(" ")[2].split("\\.")[0];
                } catch (Exception var9) {
                    var9.printStackTrace();
                    LOGGER.warn("can't parse Chrome version from system output: " + output);
                }
                break;
            case 1:
                pb = new ProcessBuilder(new String[]{"wmic", "datafile", "where", "name='C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe'", "get", "Version", "/value"});
                output = null;

                try {
                    output = readOutput(pb);
                    version = ((String) Objects.requireNonNull(output)).split("Version=")[1].split("\\.")[0];
                } catch (Exception var8) {
                    var8.printStackTrace();
                    LOGGER.warn("can't parse Chrome version from system output: " + output);
                }
                break;
            case 2:
                pb = new ProcessBuilder(new String[]{"google-chrome", "--version"});
                output = null;

                try {
                    output = readOutput(pb);
                    version = ((String) Objects.requireNonNull(output)).split(" ")[2].split("\\.")[0];
                } catch (Exception var7) {
                    var7.printStackTrace();
                    LOGGER.warn("can't parse Chrome version from system output: " + output);
                }
        }

        LOGGER.info("current chrome version: " + version);
        return version;
    }

    private static void verifyOldChromeVersion(String chromeVersion) {
        boolean newVersion;
        try {
            newVersion = Integer.parseInt(chromeVersion.split("[.]")[0]) > 110;
        } catch (Exception e) {
            newVersion = true;
        }
        if (!newVersion) {
            throw new RuntimeException("please update chrome. see README.md. current version: " + chromeVersion);
        }
    }

    private static String readOutput(ProcessBuilder pb) {
        try {
            LOGGER.info(">>> " + String.join(" ", pb.command()));
            Process p = pb.start();
            byte[] output = new byte[1024];
            p.getInputStream().read(output);
            String outputString = new String(output);
            LOGGER.info("<<< " + outputString.trim());
            return outputString;
        } catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
