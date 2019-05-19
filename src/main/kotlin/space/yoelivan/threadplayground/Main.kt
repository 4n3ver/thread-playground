package space.yoelivan.threadplayground

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component
import space.yoelivan.threadplayground.ops.Ops

@Component
class Main @Autowired constructor(private val ops: List<Ops>) : CommandLineRunner {
    companion object {
        private val log = LogManager.getLogger()
        private val TASKS = (0..100).toList()

        private fun calcPercentile(percentile: Int, list: List<Long>): Long {
            val x = percentile * list.size
            val index = x / 100
            return if (x % 100 == 0) list[index - 1]
            else (list[index - 1] + list[index] / 2)
        }

        private fun run(ops: Ops) {
            log.info("Start testing ${ops::class.java.canonicalName}")

            val start = System.currentTimeMillis()
            val res = ops.process(TASKS, start).sorted()
            val end = System.currentTimeMillis()

            log.info("Completed in ${end - start}ms")
            log.info("Min: ${res.first()}ms")
            log.info("Max: ${res.last()}ms")
            log.info("p50: ${calcPercentile(50, res)}ms")
            log.info("p95: ${calcPercentile(95, res)}ms")
            log.info("p99: ${calcPercentile(99, res)}ms\n")
        }
    }

    override fun run(vararg args: String?) {
        ops.forEach { run(it) }
    }
}
