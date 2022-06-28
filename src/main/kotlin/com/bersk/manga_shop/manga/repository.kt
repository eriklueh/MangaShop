package com.bersk.manga_shop.manga

import com.bersk.manga_shop.Manga
import com.bersk.manga_shop.MangaEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import reactor.core.publisher.Flux

@RepositoryRestResource
interface MangaRepository : ReactiveSortingRepository<MangaEntity, Long> {
    @Query(
        """
        select m.id, m.title, m.genre, a.id as author_id, a.name, a.last_name, a.birth_date, a.death_date, 
        a.nationality as author_nationality, e.id as editorial_id, e.nationality as editorial_nationality, e.name as editorial_name
        from manga m
        inner join author a on a.id = m.author
        inner join editorial e on m.editorial = e.id
         """
    )
    fun retrieveAllManga(): Flux<Manga>
}