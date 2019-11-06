package zaffranad.chatroom

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
class MainController {

    @GetMapping("/")
    fun test(): Flux<String>{
        return Flux.fromIterable(listOf("A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B","A", "B"))
                .delayElements(Duration.ofSeconds(3));
    }
}