package com.bersk.manga_shop.manga

import com.bersk.manga_shop.Editorial
import com.bersk.manga_shop.Manga
import com.bersk.manga_shop.MangaEntity
import com.bersk.manga_shop.author.AuthorService
import com.bersk.manga_shop.editorial.EditorialService
import com.bersk.manga_shop.volume.VolumePersistenceService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toFlux

@Service
class RetrieveAllManga(
    val mangaRepository: MangaRepository,
    val volumePersistenceService: VolumePersistenceService
) {
    operator fun invoke(): Flux<Manga> {
        return mangaRepository.retrieveAllManga()
            .publishOn(Schedulers.boundedElastic())
            .map { mangaWithoutVolumes ->
                volumePersistenceService.findVolumeByManga(mangaWithoutVolumes.id).collectList().block()?.let {
                    mangaWithoutVolumes.copy(volumes = it.toList())
                } ?: mangaWithoutVolumes
            }
    }
}

@Service
class CreateOrUpdate(
    val mangaRepository: MangaRepository,
    val authorService: AuthorService
) {

    operator fun invoke(manga: Manga): Mono<Manga> = Mono.just(MangaEntity.from(manga))
        .flatMap { mangaRepository.save(it) }
        .flatMap { mangaEntity ->
            authorService.findById(mangaEntity.author).map { author ->
                Manga.fromEntity(mangaEntity, author, Editorial(0, "ta", "te"))
            }
        }
}

@Service
class SaveMangaWithDependencies(
    val mangaRepository: MangaRepository,
    val authorService: AuthorService,
    val editorialService: EditorialService,
    val volumePersistenceService: VolumePersistenceService
) {
    operator fun invoke(manga: Manga): Mono<Manga> {
        return authorService.getOrCreate(manga.author).zipWith(editorialService.getOrCreate(manga.editorial)).flatMap {
            val updatedManga = manga.copy(author = it.t1, editorial = it.t2)
            mangaRepository.save(MangaEntity.from(updatedManga)).map { mEnt ->
                val volumesWithMangaId = manga.volumes.map { v -> v.copy(manga = mEnt.id) }
                volumePersistenceService.saveAll(volumesWithMangaId.toFlux())
            }.publishOn(Schedulers.boundedElastic()).map { volumes ->
                updatedManga.copy(volumes = volumes.toIterable().toList())
            }
        }
    }
}
