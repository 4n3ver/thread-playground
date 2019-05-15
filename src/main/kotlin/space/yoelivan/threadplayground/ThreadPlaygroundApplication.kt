package space.yoelivan.threadplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThreadPlaygroundApplication

fun main(args: Array<String>) {
	runApplication<ThreadPlaygroundApplication>(*args)
}
