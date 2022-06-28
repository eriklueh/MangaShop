package com.bersk.manga_shop.editorial

import com.bersk.manga_shop.Editorial
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface EditorialRepository : ReactiveSortingRepository<Editorial, Long>