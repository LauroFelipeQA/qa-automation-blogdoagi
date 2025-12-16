package agiqa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException ignored) {
        }
    }

    public static String get(String key, String defaultValue) {
        // System property tem precedência
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;

        String val = props.getProperty(key);
        return (val != null && !val.isBlank()) ? val : defaultValue;
    }
}