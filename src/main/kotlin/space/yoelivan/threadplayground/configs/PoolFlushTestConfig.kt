package space.yoelivan.threadplayground.configs

import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService

@Configuration
class PoolFlushTestConfig(pool: ExecutorService) {
    companion object {
        private val log = LogManager.getLogger()
    }

    init {
        pool.submit {
            log.info("Start PoolFlushTest")
            log.info("Pool should wait for this task to finishes before shutting down")
            Thread.sleep(60000)
            log.info("Finish PoolFlushTest")
        }
    }
}
