package no.bekk.backend

data class Todo(
    val id: String = "",
    val title: String,
    val description: String,
    val done: Boolean = false,
    val tag: String
)