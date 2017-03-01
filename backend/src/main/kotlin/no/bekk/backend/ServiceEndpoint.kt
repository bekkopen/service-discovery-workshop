package no.bekk.backend

import java.io.File
import java.util.*

class ServiceEndpoint {
    private val serviceEndpoint = System.getenv("SERVICE_ENDPOINT")

    fun serviceEndpoint(): String {
        return backendurlFromConfig() ?: serviceEndpoint
    }

    fun usingTags(): Boolean {
        return backendurlFromConfig() != null
    }

    private fun backendurlFromConfig(): String?  {
        val file = File("config.properties")
        if (file.exists()) {
            val props = Properties()
            val inputStream = file.inputStream()
            props.load(inputStream)
            inputStream.close()
            if (props.contains("backend.url")) {
                return props["backend.url"].toString()
            }
        }
        return null
    }
}