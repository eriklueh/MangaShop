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
        return authorService.getOrCreate(manga.author)
            .zipWith(editorialService.getOrCreate(manga.editorial)) //ask the services to pass an author and a editorial, join them in a tuple to create a new manga
            .flatMap {
                val updatedManga = manga.copy(
                    author = it.t1,
                    editorial = it.t2
                ) //as we are good programmers we maintain the immutability of manga by creating a copy of manga and adding properties to this copy
                mangaRepository.save(MangaEntity.from(updatedManga)).map {//save this new copy as a new manga
                    val volumeWithMangaId =
                        manga.volumes.map { volume -> volume.copy(manga = it.id) }//assign volumes to their respective manga making a copy from volume an adding the mangaID
                    volumePersistenceService.saveAll(volumeWithMangaId.toFlux())
                }.publishOn(Schedulers.boundedElastic()).map { volume ->
                    updatedManga.copy(volumes = volume.toIterable().toList())//make the flux a list
                }
            }
    }
}

@Service
class FindMangaByCriteria(
    val retrieveAllManga: RetrieveAllManga
) {
    operator fun invoke(searchCriteria: SearchCriteria): Flux<Manga> =
        retrieveAllManga().filter { searchCriteria.predicate().test(it) }

}