package com.bersk.manga_shop

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("manga")
data class MangaEntity(
    @Id
    val id: Long = 0,
    val title: String,
    val author: Long,
    val genre: String,
    val editorial: Long
) {
    companion object {
        fun from(manga: Manga): MangaEntity {
            //Fixme: Fixme chancho!
            if (manga.author.id == null || manga.editorial.id == null) throw java.lang.IllegalArgumentException("La cagaste tio!")
            return MangaEntity(
                title = manga.title,
                author = manga.author.id,
                genre = manga.genre,
                editorial = manga.editorial.id
            )
        }
    }
}

data class Manga(
    val id: Long,
    val title: String,
    val author: Author,
    val genre: String,
    val editorial: Editorial,
    val volumes: List<Volume> = listOf()
) {
    companion object {
        fun fromEntity(mangaEntity: MangaEntity, author: Author, editorial: Editorial): Manga =
            Manga(
                mangaEntity.id,
                mangaEntity.title,
                author,
                mangaEntity.genre,
                editorial
            )
    }
}

data class Editorial(
    @Id
    val id: Long? = null,
    val name: String,
    val nationality: String,
)

data class Volume(
    @Id
    val id: Long? = null,
    val publishingDate: String,
    val number: Int,
    val manga: Long = -1,
    val firstChapter: Int,
    val lastChapter: Int
)

@Table("author")
data class Author(
    @Id
    val id: Long? = null,
    val name: String,
    val lastName: String,
    val birthDate: String,
    val deathDate: String,
    val nationality: String
)



