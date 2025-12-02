void main() throws URISyntaxException, IOException {
	String input = Files.readAllLines(Path.of(ClassLoader.getSystemResource("02.input").toURI())).getFirst();
	System.out.print("2.1: ");
	Arrays.stream(input.split(",")).flatMap(s -> part1(s)).reduce(Long::sum).ifPresent(System.out::println);
	System.out.print("2.2: ");
	Arrays.stream(input.split(",")).flatMap(s -> part2(s)).reduce(Long::sum).ifPresent(System.out::println);
}

private static Stream<Long> part1(String s) {
	String[] numbers = s.split("-");

	long start = Long.parseLong(numbers[0]);
	long end = Long.parseLong(numbers[1]);

	List<Long> list = new ArrayList<>();

	for (long i = start; i <= end; i++) {
		String current = Long.toString(i);

		String s1 = current.substring(0, current.length() / 2);
		String s2 = current.substring(current.length() / 2, current.length());

		if (s1.equals(s2)) {
			list.add(i);
		}
	}

	return list.stream();
}

private static Stream<Long> part2(String s) {
	String[] numbers = s.split("-");

	long start = Long.parseLong(numbers[0]);
	long end = Long.parseLong(numbers[1]);

	List<Long> list = new ArrayList<>();

	for (long number = start; number <= end; number++) {
		String current = Long.toString(number);

		boolean added = false;
		for (int patternLength = 1; !added && patternLength <= current.length() / 2; patternLength++) {
			if (current.length() % patternLength == 0) {
				String pattern = current.substring(0, patternLength);

				boolean completePattern = true;

				int b = patternLength;
				while (completePattern && b + patternLength <= current.length()) {
					String s2 = current.substring(b, b + patternLength);

					completePattern = completePattern && s2.equals(pattern);

					b += patternLength;
				}

				if (completePattern) {
					list.add(number);
					added = true;
				}
			}
		}
	}

	return list.stream();
}
