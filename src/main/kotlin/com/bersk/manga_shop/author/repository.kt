package com.bersk.manga_shop.author

import com.bersk.manga_shop.Author
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface AuthorRepository : ReactiveSortingRepository<Author, Long>