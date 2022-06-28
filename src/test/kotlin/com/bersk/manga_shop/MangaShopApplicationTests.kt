package com.bersk.manga_shop

import io.vavr.control.Either
import io.vavr.control.Option
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono


fun toDouble(i: Int): Mono<Double> = Mono.just(i.toDouble())

//@SpringBootTest
class MangaShopApplicationTests() {

    @Test
    fun test1() {
        val data: Double? = null
        divide(1.0, 0.0)
            .fold(
                { e: Throwable -> println(e.message) },
                { r: Double -> println(r) }
            )
    }

    fun divide(a: Double, b: Double): Either<Throwable, Double> {
        return try {
            Either.right(a.div(b))
        } catch (e: java.lang.Exception) {
            Either.left(e)
        }
    }

    fun functionReceivingNullable(data: Int?) = Option.of(data)
        .map {
            it!! + 1
        }.getOrElse {
            0
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



