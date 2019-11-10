package zaffranad.chatroom

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.FluxSink
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@RestController()
@RequestMapping("messages")
class MainController() {

    var messages = HashMap<String, Message>();
    final var messagesProcessor: FluxProcessor<Message, Message> = DirectProcessor.create();
    final var messagesSink: FluxSink<Message>;

    init {
        messagesSink = messagesProcessor.sink()
    }

    @PostMapping("message")
    fun postMessage(@RequestBody dto: MessageDto) {
        val message = Message(
                UUID.randomUUID().toString(),
                dto.content,
                LocalDateTime.now(),
                dto.postedBy
        )
        messages[message.id] = message;
        messagesSink.next(message);
    }

    @GetMapping("/stream")
    fun test(): Flux<String> {
        return messagesProcessor.map { e -> "${e.content} ${e.postedDate}\n" }
    }
}