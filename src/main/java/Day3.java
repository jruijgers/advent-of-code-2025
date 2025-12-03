import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

void main() throws URISyntaxException, IOException {
	List<String> strings = Files.readAllLines(Path.of(ClassLoader.getSystemResource("03.input").toURI()));

	part1(strings);
}

private void part1(List<String> strings) {
	strings.stream().map(this::toJoltage).reduce(Integer::sum).ifPresent(System.out::println);
}

private Integer toJoltage(String s) {
	Integer tens = 0;
	int pos = -1;
	for (int i = 0; i < s.length() - 1; i++) {
		int value = Integer.parseInt(s.substring(i, i + 1));
		if (value > tens) {
			tens = value;
			pos = i;
		}
	}

	Integer digit = 0;
	for (int i = pos + 1; i < s.length(); i++) {
		int value = Integer.parseInt(s.substring(i, i + 1));
		if (value > digit) {
			digit = value;
		}
	}

	return tens * 10 + digit;
}
