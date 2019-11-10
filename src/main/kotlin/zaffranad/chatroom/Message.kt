package zaffranad.chatroom

import java.time.LocalDateTime

class Message(
        val id: String,
        val content: String,
        val postedDate: LocalDateTime,
        val postedBy: String
) {

}