package com.bersk.manga_shop.manga_collection.manga

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class AuthorService(val authorRepository: AuthorRepository) {
    fun retrieveAllAuthor(): Flux<Author> = authorRepository.findAll()

    fun createOrUpdate(author: Author): Mono<Author> = authorRepository.save(author)

    fun findById(id: Long): Mono<Author> = authorRepository.findById(id)

}

@Service
class VolumeService(val volumeRepository: VolumeRepository) {
    fun retrieveAllVolume(): Flux<Volume> = volumeRepository.findAll()

    fun findVolumeByManga(mangaId: Long): Flux<Volume> = volumeRepository.findByManga(mangaId)
}


@Service
class EditorialService(val editorialRepository: EditorialRepository) {
    fun retrieveAllEditorial(): Flux<Editorial> = editorialRepository.findAll()
}

@Service
class MangaService(
    val mangaRepository: MangaRepository,
    val authorService: AuthorService,
    val volumeService: VolumeService
) {
    fun retrieveAllManga(): Flux<Manga> {
        return mangaRepository.retrieveAllManga()
            .publishOn(Schedulers.boundedElastic())
            .map { mangaWithoutVolumes ->
                volumeService.findVolumeByManga(mangaWithoutVolumes.id).collectList().block()?.let {
                    mangaWithoutVolumes.copy(volumes = it.toList())
                } ?: mangaWithoutVolumes
            }
    }

    fun createOrUpdate(manga: Manga): Mono<Manga> = Mono.just(MangaEntity.from(manga))
        .flatMap { mangaRepository.save(it) }
        .flatMap { mangaEntity ->
            authorService.findById(mangaEntity.author)
                .map { author ->
                    Manga.fromEntity(mangaEntity, author, Editorial(0, "ta", "te", 2))
                }
        }
}