package no.bekk.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@RestController
class TagResource @Autowired constructor(val tagService: TagService) {

    val todos: MutableMap<Int, Todo> = HashMap()
    val counter: AtomicInteger = AtomicInteger(1)

    @RequestMapping(value = "/tags", method = arrayOf(RequestMethod.GET))
    fun showAll() : List<String> {
        return tagService.getTags()
    }

}