package space.yoelivan.threadplayground.configs

import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import space.yoelivan.threadplayground.ops.Ops
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
import javax.annotation.PostConstruct

@Configuration
class BoundedExecutorServiceConfig {
    companion object {
        private val log = LogManager.getLogger()

        val AVAILABLE_PROCESSORS: Int = Runtime.getRuntime().availableProcessors()
        val MAX_POOL_SIZE: Int = AVAILABLE_PROCESSORS * 64
        val MIN_POOL_SIZE: Int = AVAILABLE_PROCESSORS * 64
    }

    @Lazy
    @Bean
    fun commonBoundedPoolFactory(): ThreadPoolExecutorFactoryBean {
        log.info("Creating ExecutorService, CORE_POOL_SIZE: $MIN_POOL_SIZE MAX_POOL_SIZE: $MAX_POOL_SIZE")
        val factoryBean = ThreadPoolExecutorFactoryBean()
        factoryBean.setCorePoolSize(MIN_POOL_SIZE)
        factoryBean.setMaxPoolSize(MAX_POOL_SIZE)
        factoryBean.setRejectedExecutionHandler(CallerRunsPolicy())
        factoryBean.setKeepAliveSeconds(300)
        factoryBean.setExposeUnconfigurableExecutor(true)
        factoryBean.setAwaitTerminationSeconds(300)
        factoryBean.setWaitForTasksToCompleteOnShutdown(true)
        factoryBean.setQueueCapacity(0)
        factoryBean.setThreadGroupName("common-bounded-pool")
        factoryBean.setThreadNamePrefix("common-bounded-pool")
        return factoryBean
    }
}
