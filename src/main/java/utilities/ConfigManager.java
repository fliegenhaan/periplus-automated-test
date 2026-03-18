package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger logger = Logger.getLogger(ConfigManager.class.getName());
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    private static boolean isInitialized = false;

    private ConfigManager() {

    }

    public static void init() {
        if (!isInitialized) {
            try (InputStream input = new FileInputStream(CONFIG_FILE)) {
                properties.load(input);
                isInitialized = true;
                logger.info("Configuration loaded successfully");
            } catch (IOException e) {
                logger.severe("Failed to load configuration: " + e.getMessage());
                throw new RuntimeException("Failed to load configuration", e);
            }
        }
    }

    public static String getProperty(String key, String defaultValue) {
        if (!isInitialized) {
            init();
        }

        String value = properties.getProperty(key);

        if (value == null) {
            value = System.getProperty(key);
        }

        if (value == null) {
            value = System.getenv(key);
        }

        if (value == null) {
            value = defaultValue;
        }

        return value;
    }
    
    public static String getProperty(String key) {
        return getProperty(key, null);
    }
}
