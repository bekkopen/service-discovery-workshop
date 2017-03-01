package no.bekk.backend

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TagService {

    private val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

    fun getTags(): List<String> {
        if (!BackendApplication.serviceEndpoint.usingTags()) {
            return listOf()
        }

        try {
            println("Fetching from " + BackendApplication.serviceEndpoint)
            val response = client
                    .newCall(Request.Builder()
                            .url(BackendApplication.serviceEndpoint.serviceEndpoint() + "/v1")
                            .build())
                    .execute()
            val body = response.body()
            if (response.code() == 200) {
                val tags = Gson().fromJson(body.string(), List::class.java)
                return tags as List<String>
            }
        } catch (re: RuntimeException) {
            println("Something failed \\_(ツ)_/¯")
        }
        return listOf()
    }
}
