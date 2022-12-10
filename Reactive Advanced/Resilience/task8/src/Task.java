import java.time.Duration;
import java.util.function.Predicate;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class Task {

	static final int RETRY_COUNT = 5;
	static final String IF_MESSAGE_STARTS_WITH = "[Retry]";


	public static Publisher<String> retryWithBackoffOnError(Flux<String> publisher) {
		return publisher
				.retryWhen(
						(Retry) Retry.backoff(RETRY_COUNT, Duration.ofMillis(100))
								.maxBackoff(Duration.ofMillis(1600))
								.filter(exc -> {
									String message = exc.getMessage();
									return message != null && message.startsWith(IF_MESSAGE_STARTS_WITH);
								})
				);
	}
}