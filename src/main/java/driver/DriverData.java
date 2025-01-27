package driver;

public class DriverData {
    private static String platform;
    private static final InheritableThreadLocal<Boolean> GRANT_PERMISSION = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<String> SESSION_ID = new InheritableThreadLocal<>();


    public static String getPlatform() {
        return platform;
    }

    public static void setPlatform(String platform) {
//        PLATFORM.set(platform);
        DriverData.platform = platform;
    }


    public static boolean isChromePlatform() {
        return TestInitFactory.CHROME_PLATFORM.equals(getPlatform());
    }

    public static String getSessionId() {
        if (SESSION_ID.get() != null) {
            return SESSION_ID.get();
        }
        throw new RuntimeException("SESSION_ID isn't set");
    }

    public static void setSessionId(String sessionId) {
        SESSION_ID.set(sessionId);
    }

}
