package com.bersk.manga_shop.editorial

import com.bersk.manga_shop.Editorial
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@Validated
class EditorialController(val editorialService: EditorialService) {
    @GetMapping("/editorial")
    fun findAllEditorial(): Flux<Editorial> = editorialService.retrieveAllEditorial()

    @PutMapping("/editorial")
    fun createOrUpdate(@RequestBody editorial: Editorial): Mono<Editorial> = editorialService.createOrUpdate(editorial)
}