import java.util.stream.Collectors;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public class Task {

	public static Publisher<Tuple2<Character, Integer>> groupWordsByFirstLatter(Flux<String> words) {
		return words
				.transform(Task::groupByFirstLetter)
				.transform(Task::countLettersInWordsInGroup);
	}

	public static Flux<GroupedFlux<Character, String>> groupByFirstLetter(Flux<String> words) {
		return words.groupBy(x -> x.charAt(0));
	}

	public static Flux<Tuple2<Character, Integer>> countLettersInWordsInGroup(Flux<GroupedFlux<Character,
			String>> groupedWords) {
		return groupedWords
				.flatMap(wordsFlux-> wordsFlux
						.map(s -> {
							int count = 0;

							for (char c: s.toCharArray()) {
								if (c == wordsFlux.key()) {
									count++;
								}
							}

							return count;
						})
						.collect(Collectors.summingInt(Integer::intValue))
						.map(count -> Tuples.of(wordsFlux.key(), count))
				);
	}
}