package space.yoelivan.threadplayground.ops;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import space.yoelivan.threadplayground.BoundedExecutorServiceKt;
import space.yoelivan.threadplayground.FutureCollectors;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
class CompletableFutureOps extends Ops {
    @NotNull
    @Override
    public List<Long> process(@NotNull List<Integer> arg, long start) {
        return arg.stream()
                .map(i -> asyncCall(i, start))
                .collect(FutureCollectors.toFutureList())
                .join()
                .collect(Collectors.toList());
    }

    private CompletableFuture<Long> asyncCall(Integer i, Long start) {
        return CompletableFuture.supplyAsync(
                () -> blockingCall(i, start),
                BoundedExecutorServiceKt.getCommonBoundedPool()
        );
    }
}