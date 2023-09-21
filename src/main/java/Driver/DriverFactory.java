package Driver;


public class DriverFactory {

    public static DriverManager getDriverManager(String browserName){
        switch (browserName){
            case "Chrome" -> {
                return new ChromeDriverManager();
            }
            case "Firefox" -> {
                return new FireFoxDriverManager();
            }
            default -> throw new IllegalMonitorStateException("Invalid driver: " + browserName);
        }
    }
}
