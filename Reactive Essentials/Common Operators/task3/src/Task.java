import reactor.core.publisher.Flux;

public class Task {

	public static Flux<Character> createSequence(Flux<String> stringFlux) {
		return stringFlux.flatMap(s -> Flux.fromArray(s.split("")))
				.map(s -> s.charAt(0));
	}
}