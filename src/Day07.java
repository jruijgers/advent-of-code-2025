import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

void main() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(ClassLoader.getSystemResource("07.input").toURI()));

    var part1result = new AtomicLong();

    Map<Integer, Long> beamLocations = new HashMap<>(input.getFirst().length());
    Map<Integer, Long> newLocations = new ConcurrentHashMap<>(input.getFirst().length());

    beamLocations.put(input.getFirst().indexOf('S'), 1L);

    for (String line : input) {
        if (line.indexOf('^') > -1) {
            beamLocations.entrySet().parallelStream().forEach((entry) -> {
                if (line.charAt(entry.getKey()) == '^') {
                    newLocations.merge(entry.getKey() - 1, entry.getValue(), Long::sum);
                    newLocations.merge(entry.getKey() + 1, entry.getValue(), Long::sum);
                    part1result.incrementAndGet();
                } else {
                    newLocations.merge(entry.getKey(), entry.getValue(), Long::sum);
                }
            });
            beamLocations.clear();
            beamLocations.putAll(newLocations);
            newLocations.clear();
        }
    }

    System.out.println("7.1: " + part1result);
    beamLocations.values().parallelStream().reduce(Long::sum).ifPresent(result -> System.out.println("7.2: " + result));
}
