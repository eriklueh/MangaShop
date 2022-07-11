package com.bersk.manga_shop.manga

import com.bersk.manga_shop.Author
import com.bersk.manga_shop.Editorial
import com.bersk.manga_shop.Manga
import java.util.function.Predicate

typealias MangaPredicate = Predicate<Manga>

data class MangaCriteria(val title: String? = null)

data class AuthorCriteria(
    val name: String? = null,
    val lastName: String? = null
)

data class Criteria(val manga: MangaCriteria? = null, val author: AuthorCriteria? = null)

data class SearchCriteria(val criteria: Criteria) {

    fun predicate(): MangaPredicate {
        var predicate: MangaPredicate = Predicate<Manga> { true }
        if (criteria.author?.name != null)
            predicate = predicate.and { m: Manga -> m.author.name == criteria.author.name }
        if (criteria.author?.lastName != null)
            predicate = predicate.and { m: Manga -> m.author.lastName == criteria.author.lastName }
        if (criteria.manga?.title != null)
            predicate = predicate.and { m: Manga -> m.title == criteria.manga.title }
        return predicate
    }
}

/*fun main() {
    val someAuthor =
        Author(name = "authorName", lastName = "lastName", birthDate = "", deathDate = "", nationality = "")
    val someEditorial = Editorial(name = "editorial", nationality = "")
    val m = Manga(
        id = 0L,
        title = "Un Manga",
        author = someAuthor,
        genre = "some Genre",
        editorial = someEditorial,
        volumes = listOf()
    )

    val sc = SearchCriteria(Criteria(author = AuthorCriteria(name = "authorName")))

    val predicate = sc.predicate()

    println(predicate.test(m))
}*/