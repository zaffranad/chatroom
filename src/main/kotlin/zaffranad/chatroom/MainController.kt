package zaffranad.chatroom

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.io.Console
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@RestController
class MainController {

    var messages = HashMap<String, Message>();

    @PostMapping("/message")
    fun postMessage(@RequestBody dto: MessageDto): String {
        val message = Message(
                UUID.randomUUID().toString(),
                dto.content,
                LocalDateTime.now()
        )
        messages[message.id] = message;
        return messages.map { (_, v) -> "${v.id} ${v.content} ${v.postedDate}" }.joinToString { s -> "$s, " }
    }

    @GetMapping("/")
    fun test(): Flux<String>{
        return Flux.fromIterable(listOf("A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B"))
                .delayElements(Duration.ofSeconds(3));
    }
}