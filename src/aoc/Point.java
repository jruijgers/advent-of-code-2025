package aoc;

import java.util.Collection;
import java.util.stream.LongStream;

public class Point implements Comparable<Point> {
	long x;
	long y;

	public Point(long x, long y) {
		this.x = x;
		this.y = y;
	}

	public Point(String data) {
		String[] split = data.split(",");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
	}

	public Long area(Point other) {
		return (Math.abs(x - other.x) + 1) * (Math.abs(y - other.y) + 1);
	}


	public Collection<? extends Point> line(Point end) {
		long startX = Math.min(x, end.x);
		long endX = Math.max(x, end.x);
		long startY = Math.min(y, end.y);
		long endY = Math.max(y, end.y);

		return LongStream.rangeClosed(startX, endX)
				.mapToObj(x -> LongStream.rangeClosed(startY, endY).mapToObj(y -> new Point(x, y))).flatMap(s -> s).toList();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Point point = (Point) o;
		return x == point.x && y == point.y;
	}

	@Override
	public int hashCode() {
		int result = Long.hashCode(x);
		result = 31 * result + Long.hashCode(y);
		return result;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

	@Override
	public int compareTo(Point o) {
		int result = 0;
		result = Long.compare(x, o.x);
		if (result == 0) {
			result = Long.compare(y, o.y);
		}
		return result;
	}
}
