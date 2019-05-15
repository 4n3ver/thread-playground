package space.yoelivan.threadplayground.ops

import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class ParallelStreamOps: Ops() {
    override fun process(arg: List<Int>, start: Long): List<Long> {
        return arg.parallelStream()
                .map { i -> blockingCall(i, start)}
                .collect(Collectors.toList())
    }
}