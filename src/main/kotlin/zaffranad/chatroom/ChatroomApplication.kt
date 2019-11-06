package zaffranad.chatroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatroomApplication

fun main(args: Array<String>) {
    runApplication<ChatroomApplication>(*args)
}
