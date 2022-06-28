package com.bersk.manga_shop.appconfig

import com.bersk.manga_shop.Author
import com.bersk.manga_shop.Editorial
import com.bersk.manga_shop.Manga
import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.Row
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver

class MangaReadConverter : Converter<Row, Manga> {
    override fun convert(source: Row): Manga {
        val authorId: Int? = source.get("author_id") as Int?
        val name = source.get("name", String::class.java).toString()
        val lastName = source.get("last_name", String::class.java).toString()
        val birthDate = source.get("birth_date", String::class.java).toString()
        val deathDate = source.get("death_date", String::class.java).toString()
        val nationality = source.get("author_nationality", String::class.java).toString()
        val author = Author(authorId?.toLong() ?: 0, name, lastName, birthDate, deathDate, nationality)

        val editorialNationality = source.get("editorial_nationality", String::class.java).toString()
        val editorialId: Int? = source.get("editorial_id") as Int?
        val editorialName = source.get("editorial_name").toString()
        val editorial = Editorial(editorialId?.toLong() ?: 0, editorialName, editorialNationality)

        val title: String = source.get("title", String::class.java).toString()
        val genre = source.get("genre", String::class.java).toString()
        val mangaId: Int? = source.get("id") as Int?
        return Manga(mangaId?.toLong() ?: 0, title, author, genre, editorial)
    }
}

@Configuration
class R2DBCConfiguration {
    @Bean
    fun r2dbcCustomConversions(
        connectionFactory: ConnectionFactory,
        objectMapper: ObjectMapper
    ): R2dbcCustomConversions {
        val dialect = DialectResolver.getDialect(connectionFactory)
        val converters = listOf<Any>(MangaReadConverter())
        return R2dbcCustomConversions.of(dialect, converters)
    }
}