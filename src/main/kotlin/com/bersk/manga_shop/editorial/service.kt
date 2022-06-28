package com.bersk.manga_shop.editorial

import com.bersk.manga_shop.Editorial
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class EditorialService(val editorialRepository: EditorialRepository) {
    fun retrieveAllEditorial(): Flux<Editorial> = editorialRepository.findAll()

    fun createOrUpdate(editorial: Editorial): Mono<Editorial> = editorialRepository.save(editorial)

    fun getOrCreate(editorial: Editorial): Mono<Editorial> =
        if (editorial.id == null)
            editorialRepository.save(editorial)
        else
            editorialRepository.findById(editorial.id).switchIfEmpty {
                Mono.error(IllegalArgumentException("Editorial ID not found"))
            }
}