package aoc;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 {
	public void main() throws URISyntaxException, IOException {
		List<LightSocket> lightSockets = Files.lines(Path.of(ClassLoader.getSystemResource("08.input").toURI())).map(LightSocket::new)
				.toList();

		part1(lightSockets, 1000);
		part2(lightSockets);
	}

	private void part1(List<LightSocket> lightSockets, int limit) {

		Map<LightSocket, Set<LightSocket>> chains = lightSockets.stream()
				.collect(Collectors.toMap(ls -> ls, ls -> new HashSet<>(singletonList(ls))));

		lightSockets.stream().flatMap(a -> lightSockets.stream().filter(b -> a != b).map(b -> a.distance(b)).distinct()).distinct()
				.sorted().limit(limit).forEach(distance -> {
					Set<LightSocket> chainLeft = chains.get(distance.left);
					Set<LightSocket> chainRight = chains.get(distance.right);

					if (chainLeft != chainRight) {
						chainLeft.addAll(chainRight);
						for (LightSocket lightSocket : chainRight) {
							chains.replace(lightSocket, chainLeft);
						}
					}
				});

		chains.values().stream().distinct().map(Set::size).sorted(Collections.reverseOrder()).limit(3).reduce(Math::multiplyExact)
				.ifPresent(s -> System.out.println("8.1: " + s));
	}

	private void part2(List<LightSocket> lightSockets) {
		Map<LightSocket, Set<LightSocket>> chains = lightSockets.stream()
				.collect(Collectors.toMap(ls -> ls, ls -> new HashSet<>(singletonList(ls))));

		List<LightSocket.Distance> distances = lightSockets.stream()
				.flatMap(a -> lightSockets.stream().filter(b -> a != b).map(b -> a.distance(b)).distinct()).distinct().sorted().toList();

		for (LightSocket.Distance distance : distances) {
			Set<LightSocket> chainLeft = chains.get(distance.left);
			Set<LightSocket> chainRight = chains.get(distance.right);

			if (chainLeft != chainRight) {
				chainLeft.addAll(chainRight);
				for (LightSocket lightSocket : chainRight) {
					chains.replace(lightSocket, chainLeft);
				}
			}

			if (chains.values().stream().distinct().count() == 1) {
				System.out.println("8.2: " + (distance.left.x * distance.right.x));
				break;
			}
		}

//		System.out.println(chains);
		//		chains.values().stream().distinct().map(Set::size).sorted(Collections.reverseOrder()).limit(3).reduce(Math::multiplyExact)
		//				.ifPresent(s -> System.out.println("8.1: " + s));
	}
}
