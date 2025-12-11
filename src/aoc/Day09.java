package aoc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day09 {
	public void main() throws URISyntaxException, IOException {
		List<Point> input = Files.lines(Path.of(ClassLoader.getSystemResource("09.input").toURI())).map(Point::new).toList();

		part1(input);
//		part2(input);
	}

	private void part1(List<Point> input) {
		AtomicLong largestArea = new AtomicLong();

		input.parallelStream().forEach(l -> {
			input.parallelStream().filter(r -> l != r).forEach(r -> largestArea.accumulateAndGet(l.area(r), Math::max));
		});

		System.out.println("9.1: " + largestArea);
	}

	private void part2(List<Point> input) throws IOException {
		Collection<Point> coloredTiles = Collections.synchronizedCollection(new TreeSet<>());

		Point first = input.getFirst();

		AtomicReference<Long> minX = new AtomicReference<>(first.x);
		AtomicReference<Long> minY = new AtomicReference<>(first.x);
		AtomicReference<Long> maxX = new AtomicReference<>(first.y);
		AtomicReference<Long> maxY = new AtomicReference<>(first.y);

		coloredTiles.addAll(first.line(input.getLast()));

		IntStream.range(0, input.size() - 1).parallel().forEach(index -> {
			coloredTiles.addAll(input.get(index).line(input.get(index + 1)));

			minX.set(Math.min(input.get(index + 1).x, minX.get()));
			maxX.set(Math.max(input.get(index + 1).x, maxX.get()));
			minY.set(Math.min(input.get(index + 1).y, minY.get()));
			maxY.set(Math.max(input.get(index + 1).y, maxY.get()));
		});


//		LongStream.rangeClosed(minX.get(), maxX.get()).forEach(x -> {
//			AtomicBoolean fillIn = new AtomicBoolean(false);
//			LongStream.rangeClosed(minY.get(), maxY.get()).forEach(y -> {
//				Point p = new Point(x, y);
//				if (fillIn.get()) {
//					if (coloredTiles.contains(p)) {
//						fillIn.set(false);
//					} else {
//						coloredTiles.add(p);
//					}
//				} else if (coloredTiles.contains(p)) {
//					fillIn.set(true);
//				}
//			});
//		});

		System.out.println(Arrays.asList(coloredTiles.size(), minX, minY, maxX, maxY));

		OutputStream outputStream = new FileOutputStream("09.grid");
			LongStream.rangeClosed(minY.get(), maxY.get()).forEach(leftY -> {
				LongStream.rangeClosed(minX.get(), maxX.get()).forEach(leftX -> {
				Point p =new Point(leftX, leftY);
				try {
						outputStream.write(coloredTiles.contains(p) ? (input.contains(p) ? '#' : 'X') : ' ');
				} catch (IOException e) {
					throw new RuntimeException("Error", e); // TODO: improve catch clause
				}
				if (leftX % 1000 == 0 ) System.out.print(".");
			});
			try {
				outputStream.write('\n');
				outputStream.flush();
				System.out.println();
			} catch (IOException e) {
				throw new RuntimeException("Error", e); // TODO: improve catch clause
			}
		});

//		AtomicLong largestArea = new AtomicLong();
//
//		LongStream.rangeClosed(minX.get(), maxX.get()).forEach(leftX -> {
//			LongStream.rangeClosed(minY.get(), maxY.get()).forEach(leftY -> {
//				LongStream.rangeClosed(minX.get(), maxX.get()).forEach(rightX -> {
//					LongStream.rangeClosed(minY.get(), maxY.get()).forEach(rightY -> {
//												Point cornerNW = new Point(leftX, leftY);
//												Point cornerNE = new Point(leftX, rightY);
//												Point cornerSW = new Point(rightX, leftY);
//												Point cornerSE = new Point(rightX, rightY);
//												if (coloredTiles.containsAll(Arrays.asList(cornerNW, cornerNE, cornerSW, cornerSE))) {
//													 all corners are in the colored area
//												}
//					});
//				});
//			});
//		});

	}
}
