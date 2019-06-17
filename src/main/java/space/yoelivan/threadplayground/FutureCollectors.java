package space.yoelivan.threadplayground;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class FutureCollectors {
    public static <T, E extends CompletableFuture<T>>
    Collector<E, List<E>, CompletableFuture<Stream<T>>> toFutureList() {
        //noinspection unchecked
        return new CompletableFutureListCollector();
    }

    private static class CompletableFutureListCollector<T, E extends CompletableFuture<T>>
        implements Collector<E, List<E>, CompletableFuture<Stream<T>>> {

        @Override
        public Supplier<List<E>> supplier() {
            return LinkedList::new;
        }

        @Override
        public BiConsumer<List<E>, E> accumulator() {
            return List::add;
        }

        @Override
        public BinaryOperator<List<E>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<List<E>, CompletableFuture<Stream<T>>> finisher() {
            return listOfFutures -> CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> listOfFutures.stream())
                    .thenApply(s -> s.map(CompletableFuture::join));
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }
}
