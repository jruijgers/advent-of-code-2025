import static java.util.Collections.singletonList;

void main() throws URISyntaxException, IOException {
	List<String> input = Files.readAllLines(Path.of(ClassLoader.getSystemResource("08.input").toURI()));

	part1(input);
}

private void part1(List<String> input) {
	List<LightSocket> lightSockets = input.stream().map(LightSocket::new).toList();

	List<LightSocket.Distance> distances = lightSockets.stream()
			.flatMap(a -> lightSockets.stream().filter(b -> a != b).map(b -> a.distance(b))).sorted().collect(Collectors.toList());

	Map<LightSocket, Set<LightSocket>> chains = lightSockets.stream()
			.collect(Collectors.toMap(ls -> ls, ls -> new HashSet<>(singletonList(ls))));

	while (!distances.isEmpty()) {
		LightSocket.Distance distance = distances.removeFirst();

		Set<LightSocket> chainLeft = chains.get(distance.left());
		Set<LightSocket> chainRight = chains.get(distance.right());

		if (chainLeft != chainRight) {
			chainLeft.addAll(chainRight);
			for (LightSocket lightSocket : chainRight) {
				chains.replace(lightSocket, chainLeft);
			}
		}

		distances.removeIf(o -> o.left() == distance.left() || o.right() == distance.left() && o.left() == distance.right());
	}

	List<Integer> list = chains.values().stream().distinct().map(Set::size).sorted(Collections.reverseOrder()).toList();
	if (list.size() >= 3) {
		list.subList(0, 3).stream().reduce((l, s) -> l * s).ifPresent(s -> System.out.println("\u001B[31m8.1: " + s + "\u001B[0m"));
	}
}
