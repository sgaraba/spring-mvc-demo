package md.utm2026.mvc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class DeferredRegistry {

    private final ConcurrentMap<String, DeferredResult<Map<String, Object>>> pending = new ConcurrentHashMap<>();

    public DeferredResult<Map<String, Object>> register(String id, long delayMs) {
        DeferredResult<Map<String, Object>> deferred = new DeferredResult<>(delayMs);
        pending.put(id, deferred);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
//            Map<String, Object> payload = Map.of(
//                    "message", "DeferredResult response",
//                    "timestamp", Instant.now().toString(),
//                    "delayMs", delayMs
//            );
//            deferred.setResult(payload);

            deferred.onCompletion(scheduler::shutdownNow);
            deferred.onTimeout(() -> deferred.setErrorResult(
                    Map.of("error", "DeferredResult timeout", "delayMs", delayMs)
            ));
        }, delayMs, TimeUnit.MILLISECONDS);

        // deferred.onCompletion(() -> pending.remove(id));
        return deferred;
    }

    public boolean complete(String id, Map<String, Object> payload) {
        DeferredResult<Map<String, Object>> deferred = pending.remove(id);
        if (deferred == null) {
            return false;
        }
        deferred.setResult(payload);
        return true;
    }
}
