import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LightSocket {
	final int x;
	final int y;
	final int z;

	final Map<LightSocket, Double> distances = new HashMap<>();

	LightSocket(String data) {
		String[] split = data.split(",");
		x = Integer.parseInt(split[0]);
		y = Integer.parseInt(split[1]);
		z = Integer.parseInt(split[2]);
	}

	Distance distance(LightSocket other) {
		if (distances.containsKey(other)) {
			return new Distance(this, other, distances.get(other));
		} else if (other.distances.containsKey(this)) {
			return new Distance(this, other, other.distances.get(this));
		} else {
			double distance = Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
			distances.putIfAbsent(other, distance);
			other.distances.putIfAbsent(this, distance);
			return new Distance(this, other, distance);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		LightSocket that = (LightSocket) o;
		return x == that.x && y == that.y && z == that.z;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}

	@Override
	public String toString() {
		return "[%3d,%3d,%3d]".formatted(x, y, z);
	}

	record Distance(LightSocket left, LightSocket right, double distance) implements Comparable<LightSocket.Distance> {
		@Override
		public int compareTo(Distance other) {
			return Double.compare(distance, other.distance);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || getClass() != o.getClass())
				return false;

			Distance distance1 = (Distance) o;
			return Double.compare(distance, distance1.distance) == 0 && (Objects.equals(left, distance1.left) || Objects.equals(
					left, right)) && (Objects.equals(right, distance1.right) || Objects.equals(right, distance1.left));
		}

		@Override
		public int hashCode() {
			int result = Objects.hashCode(left);
			result = 31 * result + Objects.hashCode(right);
			result = 31 * result + Double.hashCode(distance);
			return result;
		}

		@Override
		public String toString() {
			return "{%s,%s,%3.2f}".formatted(left, right, distance);
		}
	}
}
