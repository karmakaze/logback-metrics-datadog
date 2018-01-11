package org.keithkim.logback.metrics.datadog;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.timgroup.statsd.StatsDClient;
import fresh.main.metrics.Metrics;

import java.util.ArrayList;
import java.util.List;

public class DatadogMetricsAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private final StatsDClient statsd = Metrics.statsd;

    /**
     * Create a new instrumented appender using the given registry name.
     */
    public DatadogMetricsAppender() {
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        List<String> tags = new ArrayList<>();
        tags.add("logger:" + event.getLoggerName());
        tags.add("level:" + event.getLevel().toString());

        IThrowableProxy throwable = event.getThrowableProxy();
        if (throwable != null) {
            tags.add("exception:" + throwable.getClassName());

            StringBuilder causedBy = new StringBuilder();
            for (IThrowableProxy cause = throwable.getCause(); cause != null; cause = cause.getCause()) {
                if (causedBy.length() > 0) {
                    causedBy.append(",");
                }
                causedBy.append(cause.getClassName());
            }
            if (causedBy.length() > 0) {
                tags.add("caused_by:" + causedBy);
            }
        }

        statsd.incrementCounter("metric", tags.toArray(new String[tags.size()]));
    }
}
