package zaffranad.chatroom

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@CrossOrigin
@RestController()
@RequestMapping("messages")
class MainController() {

    var messages = HashMap<String,   Message>();
    private final var messagesProcessor: FluxProcessor<Message, Message> = DirectProcessor.create();
    private final var messagesSink: FluxSink<Message>;

    init {
        messagesSink = messagesProcessor.sink()
    }

    @PostMapping("message")
    fun postMessage(@RequestBody dto: MessageDto) {
        val message = Message(
                UUID.randomUUID().toString(),
                dto.content,
                LocalDateTime.now(),
                dto.author
        )
        messages[message.id] = message;
        messagesSink.next(message);
    }

    @GetMapping("/realtime")
    fun test(): Flux<MessageDto> {
        return messagesProcessor.map {
            message -> MessageDto(message.content, message.postedDate.toString(), message.postedBy)
        }
    }
}