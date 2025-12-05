void main() throws URISyntaxException, IOException {
	List<String> lines = Files.readAllLines(Path.of(ClassLoader.getSystemResource("05.example").toURI()));

	List<Pair> freshProducts = getFreshProducts(lines);

//	part1(freshProducts, lines);
	part2(freshProducts);
}

private List<Pair> getFreshProducts(List<String> lines) {
	var products = new ArrayList<Pair>();
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

private void part1(List<Pair> freshProducts, List<String> lines) {
	long count = lines.parallelStream().filter(line -> {
		long o = Long.parseLong(line);

		for (var pair : freshProducts) {
			if (pair.isBetweenInclusive(o))
				return true;
		}
		return false;
	}).count();

	System.out.println("5.1: " + count);
}

private void part2(List<Pair> freshProducts) {
	System.out.println(freshProducts);
	System.out.println(freshProducts.stream().filter(pair -> {
		System.out.println(pair);
		for (var other : freshProducts) {
			System.out.println("\t" + other+ " => " +other.overlaps(pair));
			if (other.overlaps(pair)) {
				other.merge(pair);
				System.out.println("\t\t" + other);
				return false;
			}
		}
		return true;
	}).count());
//	freshProducts.stream().filter(p -> freshProducts.stream().anyMatch(o -> o.includes(p))).map(Pair::numberOfProducts)
//			.reduce(Long::sum).ifPresent(System.out::println);
	//	System.out.println("5.2: "+freshProducts.parallelStream().flatMap(Pair::toProductId).distinct().count());
}
