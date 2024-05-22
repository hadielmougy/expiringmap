package io.constx.expiringMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class ExpiringMapTest {

    @Test
    public void shouldExpire() throws InterruptedException {
        var map = new ExpiringMap<Integer, Object>(Duration.ofSeconds(1));
        map.put(1, new Object());
        Assertions.assertNotNull(map.get(1));
        Thread.sleep(1100);
        Assertions.assertNull(map.get(1));
    }

    @Test
    public void shouldExpire2() throws InterruptedException {
        var map = new ExpiringMap<Integer, Object>(Duration.ofSeconds(1));
        map.put(1, new Object(), Duration.ofSeconds(2));
        Assertions.assertNotNull(map.get(1));
        Thread.sleep(1100);
        Assertions.assertNotNull(map.get(1));
        Thread.sleep(1100);
        Assertions.assertNull(map.get(1));
    }
}
