package space.yoelivan.threadplayground.ops

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.Callable
import java.util.concurrent.ForkJoinPool
import java.util.stream.Collectors

@Component
class ForkJoinPoolStreamOps : Ops() {

    @Autowired
    private lateinit var asyncPool: ForkJoinPool

    override fun process(arg: List<Int>, start: Long): List<Long> {
        return asyncPool.submit(Callable<List<Long>> {
            arg.parallelStream()
                    .map { i -> blockingCall(i, start) }
                    .collect(Collectors.toList())
        }).get()
    }
}
