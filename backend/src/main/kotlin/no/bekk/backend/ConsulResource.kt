package no.bekk.backend

import com.ecwid.consul.v1.ConsulClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.web.bind.annotation.*
import java.security.Security
import java.util.*
import javax.sql.DataSource

@RestController
class ConsulResource @Autowired constructor(ds: DataSource) {

    val consulClient = ConsulClient("localhost")
    val mapper = ObjectMapper().registerModule(KotlinModule())
    val prefix = "backend/todos"

    @RequestMapping(value = "/v1", method = arrayOf(RequestMethod.GET))
    fun showAll() : List<Todo> {
        val kvValues = consulClient.getKVValues(prefix)

        if (kvValues == null || kvValues.value == null) {
            return emptyList()
        }
        return kvValues.value.map { mapper.readValue<Todo>(it.decodedValue) }
    }

    @RequestMapping(value = "v1", method = arrayOf(RequestMethod.POST))
    fun new(@RequestBody todo: Todo) : ResponseEntity<Any> {
        if (todo.id.isNotBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        val todoWithId = todo.copy(id = UUID.randomUUID().toString())
        consulClient.setKVValue(prefix + "/" + todoWithId.id, mapper.writeValueAsString(todoWithId))

        return ResponseEntity(todoWithId, HttpStatus.OK)
    }

    @RequestMapping(value = "v1", method = arrayOf(RequestMethod.PUT))
    fun update(@RequestBody todo: Todo) : ResponseEntity<Any> {
        consulClient.setKVValue(prefix + "/" + todo.id, mapper.writeValueAsString(todo))
        return ResponseEntity(todo, HttpStatus.OK)
    }

    @RequestMapping(value = "v1/{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") id: String) : ResponseEntity<Any> {
        consulClient.deleteKVValue(prefix + "/" + id)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

}