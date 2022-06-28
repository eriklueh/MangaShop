package com.bersk.manga_shop.volume

import com.bersk.manga_shop.Volume
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@Validated
class VolumeController(val volumePersistenceService: VolumePersistenceService) {
    @GetMapping("/volume")
    fun findAllVolume(): Flux<Volume> = volumePersistenceService.retrieveAllVolume()

    @PutMapping("/volume")
    fun createOrUpdate(@RequestBody volume: Volume): Mono<Volume> = volumePersistenceService.createOrUpdate(volume)
}