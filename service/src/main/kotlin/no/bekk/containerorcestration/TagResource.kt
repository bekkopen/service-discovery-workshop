package no.bekk.containerorcestration

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress

@RestController
class TagResource {
    @RequestMapping(value = "/v1", method = arrayOf(RequestMethod.GET))
    fun showAll() : List<String> {
        println(InetAddress.getLocalHost().getHostName() + " Accessed tags /v1")
        return listOf(
                "Cool",
                "Not cool",
                "Cats",
                "Smalajag")
    }
}
