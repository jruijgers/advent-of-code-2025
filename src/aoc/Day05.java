package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Day05 {
	public void main() throws URISyntaxException, IOException {
		List<String> lines = Files.readAllLines(Path.of(ClassLoader.getSystemResource("05.input").toURI()));

		Set<Pair> freshProducts = getFreshProducts(lines);

		part1(freshProducts, lines);
		part2(freshProducts);
	}

	private Set<Pair> getFreshProducts(List<String> lines) {
		var products = new HashSet<Pair>();
		var line = lines.removeFirst();

		while (!line.isEmpty()) {
			String[] ids = line.split("-");

			long x = Long.parseLong(ids[0]);
			long y = Long.parseLong(ids[1]);
			products.add(new Pair(x, y));

			line = lines.removeFirst();
		}

		return products;
	}

	private void part1(Set<Pair> freshProducts, List<String> lines) {
		long count = lines.parallelStream().filter(line -> {
			long o = Long.parseLong(line);

			return freshProducts.parallelStream().filter(p -> p.containsProduct(o)).findFirst().isPresent();
		}).count();

		System.out.println("5.1: " + count);
	}

	private void part2(Set<Pair> freshProducts) {
		for (Iterator<Pair> it = freshProducts.iterator(); it.hasNext(); ) {
			Pair pair = it.next();
			freshProducts.parallelStream().filter(pair::overlaps).findFirst().ifPresent(first -> {
				first.merge(pair);
				it.remove();
			});
		}
		freshProducts.stream().map(Pair::numberOfProducts).reduce(Long::sum).ifPresent(n -> System.out.println("5.2: " + n));
	}
}
