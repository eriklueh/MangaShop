package com.bersk.manga_shop.volume

import com.bersk.manga_shop.Volume
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import reactor.core.publisher.Flux

@RepositoryRestResource
interface VolumeRepository : ReactiveSortingRepository<Volume, Long> {
    fun findByManga(manga: Long): Flux<Volume>
}