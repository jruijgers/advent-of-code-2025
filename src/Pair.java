public class Pair {
	private long x;
	private long y;

	public Pair(long x, long y) {
		this.x = Math.min(x, y);
		this.y = Math.max(x, y);
	}

	public boolean containsProduct(long value) {
		return x <= value && value <= y;
	}

	public Long numberOfProducts() {
		return y - x + 1;
	}

	public boolean overlaps(Pair other) {
		if (this == other) {
			return false;
		}
		return x <= other.y && y >= other.x;
	}

	public void merge(Pair pair) {
		if (pair.x < x) {
			x = pair.x;
		}
		if (pair.y > y) {
			y = pair.y;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Pair pair = (Pair) o;
		return x == pair.x && y == pair.y;
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
}
