package space.yoelivan.threadplayground

import org.apache.logging.log4j.LogManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
import java.util.concurrent.TimeUnit.SECONDS


private val log = LogManager.getLogger()

val AVAILABLE_PROCESSORS: Int = Runtime.getRuntime().availableProcessors()
val MAX_POOL_SIZE: Int = AVAILABLE_PROCESSORS * 64
val MIN_POOL_SIZE: Int = AVAILABLE_PROCESSORS * 64

val commonBoundedPool: ExecutorService by lazy {
    boundedExecutorService(MIN_POOL_SIZE, MAX_POOL_SIZE)
}

fun boundedExecutorService( coreSize: Int,  maxSize: Int): ExecutorService {
    log.info("Creating ExecutorService, CORE_POOL_SIZE: $coreSize MAX_POOL_SIZE: $maxSize")
    return ThreadPoolExecutor(
            coreSize,
            maxSize,
            300,
            SECONDS,
            SynchronousQueue(),
            CallerRunsPolicy()
    )
}