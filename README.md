# Usage

```xml
<dependency>
    <groupId>io.constx</groupId>
    <artifactId>ExpiringMap</artifactId>
    <version>0.0.1</version>
</dependency>

```

```java

var map = new ExpiringMap<Integer, Object>(Duration.ofSeconds(1));
map.put(1, new Object());
```
