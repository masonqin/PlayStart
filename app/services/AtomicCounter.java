package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.*;

/**
 * This class is a concrete implementation of the {@link Counter} trait.
 * It is configured for Guice dependency injection in the {@link Module}
 * class.
 * <p>
 * This class has a {@link Singleton} annotation because we need to make
 * sure we only use one counter per application. Without this
 * annotation we would get a new instance every time a {@link Counter} is
 * injected.
 */
@Singleton
public class AtomicCounter implements Counter {

    private final AtomicInteger atomicCounter = new AtomicInteger();
    private final BlockingQueue<Map.Entry<String, JsonNode>> queue = new LinkedBlockingQueue<>(400);

    @Override
    public int nextCount() {
        return atomicCounter.getAndIncrement();
    }

    public void queuePut(Map.Entry<String, JsonNode> videoNode) {
        try {
            queue.put(videoNode);
        } catch (Exception e) {

        }
    }

    public Map.Entry<String, JsonNode> queueGet() {

        Map.Entry<String, JsonNode> ret = null;
        try {
            ret = queue.take();
        } catch (Exception e) {

        }
        return ret;
    }

    public int queueSize(){
        return queue.size();
    }

}
