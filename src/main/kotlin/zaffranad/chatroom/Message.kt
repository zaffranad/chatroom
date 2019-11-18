package zaffranad.chatroom

import java.time.LocalDateTime
import java.util.*

class Message(
        val id: String,
        val content: String,
        val postedDate: LocalDateTime,
        val author: String
) {
    constructor(content: String, postedDate: LocalDateTime, author: String) : this(UUID.randomUUID().toString(), content, postedDate, author)

}