package com.bersk.manga_shop.author

import com.bersk.manga_shop.Author
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class AuthorService(val authorRepository: AuthorRepository) {
    fun retrieveAllAuthor(): Flux<Author> = authorRepository.findAll()

    fun createOrUpdate(author: Author): Mono<Author> = authorRepository.save(author)

    fun findById(id: Long): Mono<Author> = authorRepository.findById(id)

    fun getOrCreate(author: Author): Mono<Author> =
        if (author.id == null)
            authorRepository.save(author)
        else
            authorRepository.findById(author.id).switchIfEmpty {
                Mono.error(IllegalArgumentException("Author ID not found"))
            }
}