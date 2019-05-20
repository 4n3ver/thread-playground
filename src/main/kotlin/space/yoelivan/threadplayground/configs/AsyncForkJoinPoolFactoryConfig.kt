package space.yoelivan.threadplayground.configs

import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean

@Configuration
class AsyncForkJoinPoolFactoryConfig {
    companion object {
        private val log = LogManager.getLogger()

        val AVAILABLE_PROCESSORS: Int = Runtime.getRuntime().availableProcessors()
        val CONCURRENCY_TARGET: Int = AVAILABLE_PROCESSORS * 64
    }

    @Lazy
    @Bean
    fun asyncForkJoinPool(): ForkJoinPoolFactoryBean {
        log.info("Creating ForkJoinPool, CONCURRENCY_TARGET: $CONCURRENCY_TARGET")
        val factoryBean = ForkJoinPoolFactoryBean()
        factoryBean.setParallelism(CONCURRENCY_TARGET)
        factoryBean.setAwaitTerminationSeconds(300)
        factoryBean.setAsyncMode(true)
        return factoryBean
    }
}
