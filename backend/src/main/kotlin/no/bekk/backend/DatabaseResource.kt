package no.bekk.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.web.bind.annotation.*
import javax.sql.DataSource

@RestController
class DatabaseResource @Autowired constructor(ds: DataSource) {

    val jdbcTemplate = JdbcTemplate(ds)
    val jdbcInsert = jdbcInsert()

    fun jdbcInsert(): SimpleJdbcInsert {
        val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("todo")
        jdbcInsert.setGeneratedKeyNames("id")
        return jdbcInsert
    }

    @RequestMapping(value = "/v1", method = arrayOf(RequestMethod.GET))
    fun showAll() : List<Todo> {
        return jdbcTemplate.query("SELECT * FROM todo", { rs, i ->
            Todo(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("done"),
                    rs.getString("tag")
            )
        })
    }

    @RequestMapping(value = "v1", method = arrayOf(RequestMethod.POST))
    fun new(@RequestBody todo: Todo) : ResponseEntity<Any> {
        if (todo.id != -1) {
            return ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        val input = todoToSql(todo)
        val id = jdbcInsert.executeAndReturnKey(input)

        val todoWithId = todo.copy(id = id.toInt())

        return ResponseEntity(todoWithId, HttpStatus.OK)
    }

    private fun todoToSql(todo: Todo): MapSqlParameterSource {
        val input = MapSqlParameterSource()
        input.addValue("title", todo.title)
        input.addValue("description", todo.description)
        input.addValue("done", todo.done)
        input.addValue("tag", todo.tag)
        return input
    }

    @RequestMapping(value = "v1", method = arrayOf(RequestMethod.PUT))
    fun update(@RequestBody todo: Todo) : ResponseEntity<Any> {
        jdbcTemplate.update("UPDATE todo SET title=?, description=?, done=?, tag=? WHERE id=?",
                todo.title, todo.description, todo.done, todo.tag, todo.id)

        return ResponseEntity(todo, HttpStatus.OK)
    }

    @RequestMapping(value = "v1/{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") id: Int) : ResponseEntity<Any> {
        jdbcTemplate.update("DELETE FROM todo WHERE id=?", id)

        return ResponseEntity(HttpStatus.ACCEPTED)
    }

}