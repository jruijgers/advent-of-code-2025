package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day03 {
	public void main() throws URISyntaxException, IOException {
		List<short[]> strings = Files.lines(Path.of(ClassLoader.getSystemResource("03.input").toURI())).map(this::toValues)
				.toList();

		System.out.print("3.1: ");
		strings.parallelStream().map(s -> toJoltage(s, 2)).reduce(Long::sum).ifPresent(System.out::println);
		System.out.print("3.2: ");
		strings.parallelStream().map(s -> toJoltage(s, 12)).reduce(Long::sum).ifPresent(System.out::println);
	}

	private short[] toValues(String input) {
		var values = new short[input.length()];

		for (int i = 0; i < input.length(); i++) {
			values[i] = Short.parseShort(input.substring(i, i + 1));
		}

		return values;
	}

	private long toJoltage(short[] values, int cellsToTurnOn) {
		var cells = new short[cellsToTurnOn];

		int lastCellPos = -1;
		for (int cell = 0; cell < cellsToTurnOn; cell++) {
			for (int i = lastCellPos + 1; i < values.length - cellsToTurnOn + cell + 1; i++) {
				if (values[i] > cells[cell]) {
					cells[cell] = values[i];
					lastCellPos = i;
				}
			}
		}

		long sum = 0;
		for (int i = 0; i < cellsToTurnOn; i++) {
			sum += cells[i] * Math.powExact(10L, cellsToTurnOn - i - 1);
		}
		return sum;
	}
}
