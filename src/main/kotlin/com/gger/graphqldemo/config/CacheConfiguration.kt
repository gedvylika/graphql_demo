package com.gger.graphqldemo.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Duration

@EnableCaching
@Configuration
@Profile("!test")
class CacheConfiguration {
    companion object {
        private const val MAX_RECORDS_IN_CACHE = 100_000L
        private val CACHING_DURATION = Duration.ofMinutes(10)
    }

    @Bean
    fun caffeineCacheManager(): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager("findAllPosts", "findAllComments", "findAllUsers")
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .maximumSize(MAX_RECORDS_IN_CACHE)
                .expireAfterAccess(CACHING_DURATION)
        )
        return cacheManager
    }
}
