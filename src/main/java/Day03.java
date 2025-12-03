import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

void main() throws URISyntaxException, IOException {
	List<String> strings = Files.readAllLines(Path.of(ClassLoader.getSystemResource("03.input").toURI()));

	part1(strings, 2);
	part1(strings, 12);
}

private void part1(List<String> strings, int cellsToTurnOn) {
	strings.parallelStream().map(s -> toJoltage(s, cellsToTurnOn)).reduce(Long::sum).ifPresent(System.out::println);
}

private long toJoltage(String s, int cellsToTurnOn) {
	var cells = new long[cellsToTurnOn];

	int lastCellPos = -1;
	for (int cell = 0; cell < cellsToTurnOn; cell++) {
		for (int i = lastCellPos+1; i < s.length() - cellsToTurnOn+cell+1; i++) {
			var value = Long.parseLong(s.substring(i, i + 1));
			if (value > cells[cell]) {
				cells[cell] = value;
				lastCellPos = i;
			}
		}
	}

	long sum = 0;
	for (int i = 0; i < cells.length; i++) {
		sum += cells[i] * Math.powExact(10L ,cellsToTurnOn-i-1);
	}
	return sum;
}
