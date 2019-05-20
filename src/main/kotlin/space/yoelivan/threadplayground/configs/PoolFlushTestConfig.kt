package space.yoelivan.threadplayground.configs

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService

@Configuration
class PoolFlushTestConfig(@Qualifier("asyncForkJoinPool") pool: ExecutorService) {
    companion object {
        private val log = LogManager.getLogger()
    }

    init {
        pool.submit {
            log.info("Start PoolFlushTest")
            log.info("Pool should wait for this task to finishes before shutting down")
            Thread.sleep(30000)
            log.info("Finish PoolFlushTest")
        }
    }
}
