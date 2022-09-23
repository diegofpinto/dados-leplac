import java.io.*;
import java.util.Properties;
import java.util.Set;

public class PropertiesCache {
    private final Properties configProp = new Properties();
    private final String configFilePath = "config.properties";

    private PropertiesCache() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder {
        private static final PropertiesCache INSTANCE = new PropertiesCache();
    }

    public static PropertiesCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setProperty(String key, String value) {
        configProp.setProperty(key, value);
    }

    public void flush() throws FileNotFoundException, IOException {
        try (final OutputStream outputstream
                     = new FileOutputStream(configFilePath);) {
            configProp.store(outputstream, "File Updated");
            outputstream.close();
        }
    }
    
    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return configProp.containsKey(key);
    }


}