package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day09 {
	public void main() throws URISyntaxException, IOException {
		List<Pair> input = Files.lines(Path.of(ClassLoader.getSystemResource("09.input").toURI())).map(Pair::new).toList();

		part1(input);
	}

	private void part1(List<Pair> input) {
		AtomicLong largestArea = new AtomicLong();

		input.parallelStream().forEach(l -> {
			input.parallelStream().filter(r -> l != r).forEach(r -> largestArea.accumulateAndGet(l.area(r), Math::max));
		});

		System.out.println("9.1: " + largestArea);
	}
}
