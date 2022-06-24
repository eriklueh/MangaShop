package com.bersk.manga_shop.manga_collection.manga

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

@RestController
@Validated
class VolumeController(val volumeService: VolumeService, val volumeRepository: VolumeRepository) {
    @GetMapping("/volume")
    fun findAllVolume(): Flux<Volume> = volumeService.retrieveAllVolume()

    @GetMapping("/hi")
    fun hi(): Flux<Volume> {
        return volumeRepository.findByManga(2)
    }
}


@RestController
@Validated
class EditorialController(val editorialService: EditorialService) {
    @GetMapping("/editorial")
    fun findAllEditorial(): Flux<Editorial> = editorialService.retrieveAllEditorial()
}

@RestController
@Validated
class MangaController(val mangaService: MangaService) {
    @GetMapping("/manga")
    fun findAllManga(): Flux<Manga> = mangaService.retrieveAllManga()

    @PutMapping("/manga")
    fun createOrUpdate(@RequestBody manga: Manga): Mono<Manga> = mangaService.createOrUpdate(manga)
}