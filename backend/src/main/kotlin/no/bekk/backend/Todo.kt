package no.bekk.backend

data class Todo(
    val id: Int = -1,
    val title: String,
    val description: String,
    val done: Boolean = false,
    val tag: String
)