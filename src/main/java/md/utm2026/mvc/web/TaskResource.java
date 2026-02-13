package md.utm2026.mvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import md.utm2026.mvc.service.DeferredRegistry;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import java.time.LocalDate;

@Controller
@ResponseBody
@RequestMapping("/api/task")
public class TaskResource {

    private static final Logger logger = LoggerFactory.getLogger(TaskResource.class);

    private final DeferredRegistry deferredRegistry;

    public TaskResource(DeferredRegistry deferredRegistry) {
        this.deferredRegistry = deferredRegistry;
    }

    @GetMapping("/v1")
    public String testV1() {
        return "Hello task v1";
    }

    @GetMapping("/v2")
    public String testV2() {
        return "Hello task v2";
    }

    @GetMapping("/by-date")
    public String getByDate(@RequestParam LocalDate date) {
        return "Task date: " + date;
    }

    @GetMapping("/callable")
    public Callable<Map<String, Object>> getCallable(@RequestParam(defaultValue = "2000") long delayMs) {
        return () -> {
            logger.info("Run callable ...");
            Thread.sleep(delayMs);
            return Map.of(
                    "message", "Callable response",
                    "timestamp", Instant.now().toString(),
                    "delayMs", delayMs
            );
        };
    }

    @GetMapping("/async")
    public ResponseEntity<Void> getAsync() throws InterruptedException {
        deferredRegistry.test();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deferred/{id}")
    public DeferredResult<Map<String, Object>> getDeferred(
            @PathVariable String id,
            @RequestParam(defaultValue = "10000") long timeoutMs
    ) {
        return deferredRegistry.register(id, timeoutMs);
    }

    @GetMapping("/deferred/{id}/complete")
    public Map<String, Object> completeDeferred(
            @PathVariable String id,
            @RequestParam(defaultValue = "ok") String message
    ) {
        Map<String, Object> payload = Map.of(
                "message", message,
                "timestamp", Instant.now().toString()
        );
        boolean completed = deferredRegistry.complete(id, payload);
        return Map.of("completed", completed);
    }

    @GetMapping("/sse")
    public SseEmitter streamUpdates(
            @RequestParam(defaultValue = "1000") long intervalMs,
            @RequestParam(required = false) Integer maxEvents
    ) {
        SseEmitter emitter = new SseEmitter(0L);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        AtomicInteger sent = new AtomicInteger(0);

        Runnable tickTask = () -> {
            int count = sent.incrementAndGet();
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("count", count);
            payload.put("timestamp", Instant.now().toString());
            payload.put("status", "ok");
            try {
                emitter.send(SseEmitter.event()
                        .name("tick")
                        .data(payload));
                if (maxEvents != null && count >= maxEvents) {
                    emitter.send(SseEmitter.event()
                            .name("done")
                            .data(Map.of("total", count)));
                    emitter.complete();
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        };

        scheduler.scheduleAtFixedRate(tickTask, 0L, intervalMs, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("ping")
                        .data(Map.of("timestamp", Instant.now().toString())));
            } catch (Exception ex) {
                ex.printStackTrace();
                emitter.completeWithError(ex);
            }
        }, 15_000L, 15_000L, TimeUnit.MILLISECONDS);

        emitter.onCompletion(() -> {
            logger.info("End SSE ...");
            scheduler.shutdownNow();
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            scheduler.shutdownNow();
        });
        return emitter;
    }
}
