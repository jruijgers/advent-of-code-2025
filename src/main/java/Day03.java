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

private long toJoltage(String input, int cellsToTurnOn) {
	var values = new long[input.length()];
	var cells = new long[cellsToTurnOn];

	for (int i = 0; i < input.length(); i++) {
		values[i] = Long.parseLong(input.substring(i, i + 1));
	}

	int lastCellPos = -1;
	for (int cell = 0; cell < cellsToTurnOn; cell++) {
		for (int i = lastCellPos + 1; i < input.length() - cellsToTurnOn + cell + 1; i++) {
			if (values[i] > cells[cell]) {
				cells[cell] = values[i];
				lastCellPos = i;
			}
		}
	}

	long sum = 0;
	for (int i = 0; i < cells.length; i++) {
		sum += cells[i] * Math.powExact(10L, cellsToTurnOn - i - 1);
	}
	return sum;
}
