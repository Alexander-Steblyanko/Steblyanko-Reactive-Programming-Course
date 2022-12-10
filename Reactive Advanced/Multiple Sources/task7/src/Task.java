import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class Task {

	public static Publisher<String> combineSeveralSources(Publisher<String> prefixPublisher,
			Publisher<String> wordPublisher,
			Publisher<String> suffixPublisher) {

		return Flux.combineLatest((words) -> "" + words[0] + words[1] + words[2],
				prefixPublisher, wordPublisher, suffixPublisher);
	}
}