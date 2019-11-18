package zaffranad.chatroom

import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.FluxSink
import java.time.LocalDateTime

@CrossOrigin
@RestController()
@RequestMapping("messages")
class MainController() {

    var messages = HashMap<String, Message>();
    private final var messagesProcessor: FluxProcessor<Message, Message> = DirectProcessor.create();
    private final var messagesSink: FluxSink<Message>;

    init {
        messagesSink = messagesProcessor.sink()
    }

    @PostMapping("message")
    fun postMessage(@RequestBody dto: MessageDto) {
        val message = Message(
                dto.content,
                LocalDateTime.now(),
                dto.author
        )
        messages[message.id] = message;
        messagesSink.next(message);
    }

    @GetMapping("/realtime")
    fun messageStream(): Flux<ServerSentEvent<MessageDto>> {
        return messagesProcessor.map {
            message -> MessageDto(message.content, formatter.format(message.postedDate), message.author)
        }.map {
            dto -> ServerSentEvent.builder(dto).build()
        }
    }
}