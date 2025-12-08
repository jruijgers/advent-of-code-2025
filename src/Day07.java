import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

void main() throws URISyntaxException, IOException {
    List<String> input = Files.readAllLines(Path.of(ClassLoader.getSystemResource("07.example").toURI()));

    part1(input);
}

private void part1(List<String> input) {
    var result = new AtomicInteger();

    Set<Integer> beamLocations = new HashSet<>(input.getFirst().length());
    Set<Integer> newLocations = new HashSet<>(input.getFirst().length());

    beamLocations.add(input.getFirst().indexOf('S'));

    for(String line : input) {
        beamLocations.parallelStream().forEach(beamLocation -> {
            if (line.charAt(beamLocation) == '^') {
                if (beamLocation -1 >= 0) {
                    newLocations.add(beamLocation - 1);
                }
                if (beamLocation +1 < input.size()) {
                    newLocations.add(beamLocation + 1);
                }
                result.incrementAndGet();
            } else {
                newLocations.add(beamLocation);
            }
        });
        beamLocations.clear();
        beamLocations.addAll(newLocations);
        System.out.println(beamLocations);
        newLocations.clear();
    }

    System.out.println("7.1: "+result);
}
