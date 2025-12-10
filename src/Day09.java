void main() throws URISyntaxException, IOException {
	List<Pair> input = Files.lines(Path.of(ClassLoader.getSystemResource("09.input").toURI())).map(Pair::new).toList();

	part1(input);
}

private void part1(List<Pair> input){
	AtomicLong largestArea = new AtomicLong();

	input.parallelStream().forEach(l -> {
		input.parallelStream().forEach(r ->{
			if (l != r) {
				System.out.println(l+" " + r + " " + l.area(r));
				largestArea.accumulateAndGet(l.area(r), Math::max);
			}
		});
	});

	System.out.println("09.1: " + largestArea);
}
