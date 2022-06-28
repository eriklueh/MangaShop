package com.bersk.manga_shop.volume

import com.bersk.manga_shop.Volume
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class VolumePersistenceService(val volumeRepository: VolumeRepository) {
    fun retrieveAllVolume(): Flux<Volume> = volumeRepository.findAll()

    fun findVolumeByManga(mangaId: Long): Flux<Volume> = volumeRepository.findByManga(mangaId)

    fun createOrUpdate(volume: Volume): Mono<Volume> = volumeRepository.save(volume)

    fun saveAll(volumes: Flux<Volume>) = volumeRepository.saveAll(volumes)

}