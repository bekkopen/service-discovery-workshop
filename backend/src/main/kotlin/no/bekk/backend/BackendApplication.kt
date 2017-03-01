package no.bekk.backend

import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import java.net.InetAddress
import javax.sql.DataSource

@SpringBootApplication
open class BackendApplication {

    companion object {
        val jdbcUrl = System.getenv("JDBC_URL") ?: "jdbc:h2:mem:test"
        val username = System.getenv("JDBC_USERNAME") ?: "user"
        val password = System.getenv("JDBC_PASSWORD") ?: "pass"
        val hostname = InetAddress.getLocalHost().getHostName()
        val serviceEndpoint = ServiceEndpoint()
    }

    @Bean
    open fun corsFilter(): FilterRegistrationBean {
        val registration = FilterRegistrationBean()
        registration.setFilter(CorsFilter())
        registration.addUrlPatterns("*")
        registration.setName("corsFilter")
        return registration
    }

    @Bean
    open fun datasource(): DataSource {
        val pool = HikariDataSource()

        pool.jdbcUrl = jdbcUrl
        pool.username = username
        pool.password = password
        return pool
    }

    @Bean
    @Autowired
    open fun liquibase(ds: DataSource): SpringLiquibase {
        val liquibase = SpringLiquibase();

        liquibase.dataSource = ds
        liquibase.changeLog = "classpath:db-changelog.xml"

        return liquibase
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}
