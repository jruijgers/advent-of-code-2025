package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day02 {
	public void main() throws URISyntaxException, IOException {
		String input = Files.readAllLines(Path.of(ClassLoader.getSystemResource("02.input").toURI())).getFirst();
		List<Long> numbers = Arrays.stream(input.split(",")).flatMap(s -> toRange(s)).toList();
		System.out.print("2.1: ");
		numbers.parallelStream().filter(s -> part1(s)).reduce(Long::sum).ifPresent(System.out::println);
		System.out.print("2.2: ");
		numbers.parallelStream().filter(s -> part2(s)).reduce(Long::sum).ifPresent(System.out::println);
	}

	private Stream<Long> toRange(String s) {
		String[] numbers = s.split("-");

		return LongStream.rangeClosed(Long.parseLong(numbers[0]), Long.parseLong(numbers[1])).boxed();
	}

	private boolean part1(Long number) {
		String current = Long.toString(number);

		return current.substring(0, current.length() / 2).equals(current.substring(current.length() / 2));
	}

	private boolean part2(Long number) {
		String current = Long.toString(number);

		for (int patternLength = 1; patternLength <= current.length() / 2; patternLength++) {
			if (current.length() % patternLength == 0) {
				String pattern = current.substring(0, patternLength);

				boolean completePattern = true;

				for (int b = patternLength; completePattern && b + patternLength <= current.length(); b += patternLength) {
					String s2 = current.substring(b, b + patternLength);

					completePattern = completePattern && s2.equals(pattern);
				}

				if (completePattern) {
					return true;
				}
			}
		}

		return false;
	}
}
