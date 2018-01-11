package fresh.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        log.info("started with args: {}", asList(args));

        log.warn("warn message", new Throwable());
        log.error("error message", new Throwable(new Throwable(new Throwable())));
        log.debug("debug message", new Throwable());
        log.trace("trace message", new Throwable());

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("Interrupted while sleeping", e);
        }
    }
}
