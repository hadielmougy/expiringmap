package io.constx.expiringMap;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public final class ExpiringMap<K, V> {

    private final ConcurrentMap<K, V> delegate = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private final Duration ttl;

    public ExpiringMap() {
        this(Duration.ofSeconds(5));
    }

    public ExpiringMap(Duration ttl) {
        this.ttl = ttl;
    }


    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    public V get(Object key) {
        return delegate.get(key);
    }

    public V put(K key, V value) {
        long millis = ttl.toMillis();
        scheduler.schedule(() -> remove(key), millis, TimeUnit.MILLISECONDS);
        return delegate.put(key, value);
    }

    public V put(K key, V value, Duration duration) {
        long millis = duration.toMillis();
        scheduler.schedule(() -> remove(key), millis, TimeUnit.MILLISECONDS);
        return delegate.put(key, value);
    }

    public V remove(Object key) {
        return delegate.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    public void putAll(Map<? extends K, ? extends V> m, Duration duration) {
        m.forEach((k, v) -> put(k, v, duration));
    }

    public void clear() {
        delegate.clear();
    }

    public Set<K> keySet() {
        return delegate.keySet();
    }

    public Collection<V> values() {
        return delegate.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }
}
