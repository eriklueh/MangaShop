package com.bersk.manga_shop.manga

import com.bersk.manga_shop.Manga
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@Validated
class MangaController(
    val createOrUpdate: CreateOrUpdate,
    val retrieveAllManga: RetrieveAllManga,
    val saveMangaWithDependencies: SaveMangaWithDependencies,
    val findMangaByCriteria: FindMangaByCriteria

) {

    @GetMapping("/manga")
    fun findAll(): Flux<Manga> = retrieveAllManga()

    @PutMapping("/manga")
    fun createOrUpdateManga(@RequestBody manga: Manga): Mono<Manga> = createOrUpdate(manga)

    @PutMapping("/mangaWithDeps")
    fun mangaWithDependencies(@RequestBody manga: Manga): Mono<Manga> {
        return saveMangaWithDependencies(manga)
    }

    @PostMapping("/manga/findBy")
    fun showManga(@RequestBody searchCriteria: SearchCriteria): Flux<Manga> {
        return findMangaByCriteria(searchCriteria)
    }

}

