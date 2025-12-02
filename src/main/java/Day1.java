void main() throws IOException, URISyntaxException {
	List<String> lines = Files.readAllLines(Path.of(ClassLoader.getSystemResource("01.input").toURI()));

	part1(lines);
	part2(lines);
}

private static void part1(List<String> lines) throws URISyntaxException, IOException {
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

private static void part2(List<String> lines) throws URISyntaxException, IOException {
	AtomicInteger numberOfTimeZero = new AtomicInteger();
	AtomicInteger dialPosition = new AtomicInteger(50);
	lines.forEach(s -> {
		int change = Integer.parseInt(s.substring(1));
		numberOfTimeZero.addAndGet(change / 100);
		change = change % 100;
		int newDialPosition = dialPosition.get();
		if (s.startsWith("R")) {
			newDialPosition = (newDialPosition + change) % 100;
			if (newDialPosition == 0 || (dialPosition.get()!=0 && newDialPosition < dialPosition.get())) {
				numberOfTimeZero.incrementAndGet();
			}
		} else {
			newDialPosition = (newDialPosition - change+100) % 100;
			if (newDialPosition == 0 || (dialPosition.get()!=0 && newDialPosition > dialPosition.get())) {
				numberOfTimeZero.incrementAndGet();
			}
		}
		dialPosition.set(newDialPosition);
	});
	System.out.println("1.2: " + numberOfTimeZero.get());
}
