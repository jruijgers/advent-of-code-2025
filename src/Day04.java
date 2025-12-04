void main() throws URISyntaxException, IOException {
    List<char[]> lines = Files.lines(Path.of(ClassLoader.getSystemResource("04.input").toURI())).map(String::toCharArray).toList();

    part1(lines);
    part2(lines);
}

private void part1(List<char[]> lines) {
    System.out.println("4.1: " + getSafeToAccess(lines));
}

private void part2(List<char[]> lines) {
    int total = 0;
    int safeToAccess = Integer.MAX_VALUE;

    while(safeToAccess >0) {
        safeToAccess = getSafeToAccess(lines);

        for (char[] line : lines) {
            for (int y = 0; y < line.length; y++) {
                if (line[y] == 'X') {
                    line[y] = '.';
                }
            }
        }

        total += safeToAccess;
    }

    System.out.println("4.2: " + total);
}

private int getSafeToAccess(List<char[]> lines) {
    var safeToAccess = 0;
    int lineLength = lines.getFirst().length;
    for (int x = 0; x < lines.size(); x++) {
        for (int y = 0; y < lineLength; y++) {
            if (canLift(x, y, lines)) {
                safeToAccess++;
            }
        }
    }
    return safeToAccess;
}

private boolean canLift(int x, int y, List<char[]> lines) {
    var chars = new char[]{'@', 'X'};
    if (Arrays.binarySearch(chars, lines.get(x)[y]) < 0) {
        return false;
    }

    int lineLength = lines.getFirst().length;
    AtomicInteger occupiedPlaces = new AtomicInteger();

//    System.out.print("["+x+","+y+"] =>");
    IntStream.rangeClosed(x - 1, x + 1).forEach(newX -> {
        IntStream.rangeClosed(y - 1, y + 1).forEach(newY -> {
            if (newX >= 0 && newX < lines.size() && newY >= 0 && newY < lineLength && !(newX == x && newY == y)) {
//                System.out.print(" ["+newX+","+newY+"]="+lines.get(newX)[newY]);
                if (Arrays.binarySearch(chars, lines.get(newX)[newY]) >= 0) {
                    occupiedPlaces.getAndIncrement();
                }
            }
        });
    });
//    System.out.println(" => "+ occupiedPlaces.get());

    if (occupiedPlaces.get() < 4) {
        lines.get(x)[y] = 'X';
        return true;
    }
    return false;
}