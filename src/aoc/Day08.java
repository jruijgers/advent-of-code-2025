package aoc;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 {
	public void main() throws URISyntaxException, IOException {
		List<String> input = Files.readAllLines(Path.of(ClassLoader.getSystemResource("08.input").toURI()));

		part1(input);
	}

	private void part1(List<String> input) {
		List<LightSocket> lightSockets = input.stream().map(LightSocket::new).toList();

		List<LightSocket.Distance> distances = lightSockets.stream()
				.flatMap(a -> lightSockets.stream().filter(b -> a != b).map(b -> a.distance(b)).distinct()).distinct().sorted()
				.toList();

		Map<LightSocket, Set<LightSocket>> chains = lightSockets.stream()
				.collect(Collectors.toMap(ls -> ls, ls -> new HashSet<>(singletonList(ls))));

		System.out.println(distances.size());
		int i = 0;
		for (LightSocket.Distance distance : distances) {
			Set<LightSocket> chainLeft = chains.get(distance.left);
			Set<LightSocket> chainRight = chains.get(distance.right);

			//			System.out.println(++i + " => "+ distance);

			if (chainLeft != chainRight) {
				chainLeft.addAll(chainRight);
				for (LightSocket lightSocket : chainRight) {
					chains.replace(lightSocket, chainLeft);
				}
			}
		}

		List<Integer> list = chains.values().stream().distinct().map(Set::size).sorted(Collections.reverseOrder()).toList();
		System.out.println(list);
		if (list.size() >= 3) {
			list.subList(0, 3).stream().reduce(Math::multiplyExact)
					.ifPresent(s -> System.out.println("\u001B[31m8.1: " + s + "\u001B[0m"));
		}
	}
}
