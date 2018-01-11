package fresh.main.metrics;

import org.keithkim.logback.metrics.datadog.DatadogMetricsAppender;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BuildVersion {
    public final String REVISION;

    private BuildVersion() {
        Properties prop = new Properties();
        String revision = null;
        try (InputStream in = DatadogMetricsAppender.class.getResourceAsStream("/build.properties")) {
            prop.load(in);
            revision = prop.getProperty("revision");
        } catch (IOException e) {
        }
        if (revision == null || revision.isEmpty()) {
            revision = "(unknown)";
        }
        REVISION = revision;
    }

    private static class LazyHolder {
        static final BuildVersion INSTANCE = new BuildVersion();
    }

    public static BuildVersion getInstance() {
        return LazyHolder.INSTANCE;
    }
}