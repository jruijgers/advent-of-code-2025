void main() throws URISyntaxException, IOException {
    List<String> lines = Files.readAllLines(Path.of(ClassLoader.getSystemResource("06.input").toURI()));

    String[] operations = lines.removeLast().trim().split(" +");

    part1(lines, operations);
    part2(lines, operations);
}

private void part1(List<String> lines,  String[] operations)  {
    List<String[]> data = lines.stream().map(l -> l.trim().split(" +")).toList();
    var total = new AtomicLong();
    IntStream.range(0, data.getFirst().length).forEach(i -> {
        var result = -1L;
        var operation = operations[i];
        for (String[] datum : data) {
            long value = Long.parseLong(datum[i]);
            if (result < 0L) {
                result = value;
            } else if ("+".equals(operation)) {
                result += value;
            } else {
                result *= value;
            }
        }
        total.addAndGet(result);
    });

    System.out.println("6.1: " + total.get());
}

private void part2(List<String> lines, String[] operations) {
    var total = new AtomicLong();

    Integer length = lines.stream().map(String::length).max(Integer::compareTo).get();

    Queue<String> queue = new LinkedList<>(Arrays.asList(operations));
    List<Long> values = new LinkedList<>();
    IntStream.range(0, length).forEach(i -> {
       StringBuilder value = new StringBuilder();
       for(String line : lines) {
           if (i < line.length()) {
           value.append(line.charAt(i));
           }
       }

       if (value.toString().trim().isEmpty()) {
           performOperation(queue, values, total);
           values.clear();
       } else {
           values.add(Long.parseLong(value.toString().trim()));
       }
    });

    performOperation(queue, values, total);

    System.out.println("6.2: " + total.get());
}

private static void performOperation(Queue<String> queue, List<Long> values, AtomicLong total) {
    String poll = queue.poll();
    if (Objects.equals(poll, "+")) {
        values.stream().reduce(Long::sum).ifPresent(total::addAndGet);
    } else {
        values.stream().reduce(Math::multiplyExact).ifPresent(total::addAndGet);
    }
}