package no.bekk.backend

import java.io.File
import java.util.*

class ServiceEndpoint {
    private val serviceEndpoint = System.getenv("SERVICE_ENDPOINT")

    fun serviceEndpoint(): List<String> {
        if (backendurlFromConfig().isEmpty())
            return listOf(serviceEndpoint).filterNotNull()
        else
            return backendurlFromConfig()
    }

    fun usingTags(): Boolean {
        return backendurlFromConfig().isNotEmpty()
    }

    private fun backendurlFromConfig(): List<String>  {
        val file = File("config.properties")
        if (file.exists()) {
            val props = Properties()
            val inputStream = file.inputStream()
            props.load(inputStream)
            inputStream.close()
            if (props.containsKey("service.url")) {
                return props["service.url"].toString()
                        .split(",")
                        .filter { it.isNotBlank() }
                        .map {
                            if (it.startsWith("http://")) it
                            else "http://" + it
                        }
            }
        }
        return emptyList()
    }
}