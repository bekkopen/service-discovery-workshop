package no.bekk.backend

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InfoResource {

    @RequestMapping("/info")
    fun jdbcUrl(): Info {
        val isInMemory = BackendApplication.jdbcUrl.contains(":h2:")
        return Info(
                isInMemory,
                BackendApplication.jdbcUrl,
                BackendApplication.hostname,
                BackendApplication.serviceEndpoint.usingTags(),
                BackendApplication.serviceEndpoint.serviceEndpoint()
        )
    }

    data class Info(
            val jdbcInMemory: Boolean,
            val jdbcUrl: String,
            var hostname: String,
            val usesTags: Boolean,
            val serviceEndpoint: List<String>
    )
}