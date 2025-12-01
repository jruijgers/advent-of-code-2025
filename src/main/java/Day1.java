void main() throws IOException, URISyntaxException {
	Stream<String> lines = Files.lines(Path.of(ClassLoader.getSystemResource("01-1.txt").toURI()));

	AtomicInteger numberOfTimeZero = new AtomicInteger();
	AtomicInteger dialPosition = new AtomicInteger(50);
	lines.forEach(s -> {
		int change = Integer.parseInt(s.substring(1));
		int newDialPosition = dialPosition.get();
		if (s.startsWith("R")) {
			newDialPosition = (newDialPosition + change) % 100;
		} else {
			newDialPosition = (newDialPosition - change + 100) % 100;
		}

		if (newDialPosition == 0) {
			numberOfTimeZero.incrementAndGet();
		}
		dialPosition.set(newDialPosition);
	});

	System.out.println("1.1: " + numberOfTimeZero.get());
}
