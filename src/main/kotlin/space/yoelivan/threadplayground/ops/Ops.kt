package space.yoelivan.threadplayground.ops

import org.apache.logging.log4j.LogManager

abstract class Ops {
    companion object {
        private val log = LogManager.getLogger()
    }

    abstract fun process(arg: List<Int>, start: Long): List<Long>

    protected fun blockingCall(i: Int, start: Long): Long {
        log.trace("Start blockingCall($i)")
        Thread.sleep(1000)
        val duration = System.currentTimeMillis() - start
        log.trace("Finished blockingCall($i) in ${duration}ms")
        return duration
    }
}