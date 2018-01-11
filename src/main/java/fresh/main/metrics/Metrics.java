package fresh.main.metrics;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.ServiceCheck;
import com.timgroup.statsd.StatsDClient;
import fresh.main.metrics.BuildVersion;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class Metrics {
    public static final StatsDClient statsd = new NonBlockingStatsDClient(
            "fresh",
            "localhost",
            8125,
            "version:" + BuildVersion.getInstance().REVISION,
            "spring_profile:" + System.getenv("SPRING_PROFILES_ACTIVE"),
            "instance:" + metricsInstance());

    private static String metricsInstance() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return UUID.randomUUID().toString();
        }

    }

    static {
        statsd.incrementCounter("service_started");

        ServiceCheck sc = ServiceCheck
                .builder()
                .withName("service_check")
                .withStatus(ServiceCheck.Status.OK)
                .build();
        statsd.serviceCheck(sc);
    }
}
