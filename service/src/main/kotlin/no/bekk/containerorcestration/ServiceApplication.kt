package no.bekk.containerorcestration

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class BackendApplication {
}

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}
