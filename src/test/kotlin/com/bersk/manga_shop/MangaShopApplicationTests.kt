package com.bersk.manga_shop

import com.bersk.manga_shop.manga_collection.manga.Author
import com.bersk.manga_shop.manga_collection.manga.AuthorRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono


fun toDouble(i: Int): Mono<Double> = Mono.just(i.toDouble())

@SpringBootTest
class MangaShopApplicationTests(val authorRepository: AuthorRepository) {

    @Test
    fun test1() {
        authorRepository.save(Author(1, "Gege", "Akutami", "tal", "ta", "japo"))
    }

/*   @Test
   fun contextLoads() {
/       val mono: Mono<Int> = Mono.just(1)
       mono.doOnNext { it -> println(it) }

       Flux.range(1, 10)
           .flatMap {
               toDouble(it)
           }
           .map {
               try {
                   it?.let {
                       if (it > 3) throwa Exception("LAlala")
                       sqrt(it)
                   }
               } catch (e: Exception) {
                   Flux.error<Throwable>(e)
               }

           }
           .doOnError {
               println(it.message)
           }
           .subscribe { println(it) }*/
   }



