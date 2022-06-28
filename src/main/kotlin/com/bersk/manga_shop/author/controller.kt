package com.bersk.manga_shop.author

import com.bersk.manga_shop.Author
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@Validated
class AuthorController(val authorService: AuthorService) {
    @GetMapping("/author")
    fun findAllAuthor(): Flux<Author> = authorService.retrieveAllAuthor()

    @PutMapping("/author")
    fun createOrUpdate(@RequestBody author: Author): Mono<Author> = authorService.createOrUpdate(author)
}