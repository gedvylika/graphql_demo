package com.gger.graphqldemo.service

import com.gger.graphqldemo.model.Comment
import com.gger.graphqldemo.model.Post
import com.gger.graphqldemo.model.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DataProviderService(
    private val restTemplate: RestTemplate
) {
    companion object {
        const val GET_POSTS_URL = "https://jsonplaceholder.typicode.com/posts"
        const val GET_COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments"
        const val GET_USERS_URL = "https://jsonplaceholder.typicode.com/users"
    }

    @Cacheable("findAllPosts", cacheManager = "caffeineCacheManager")
    fun findAllPosts(): List<Post> {

        val posts = restTemplate.getForEntity(GET_POSTS_URL, Array<Post>::class.java)
            .body?.toList()

        return posts ?: listOf()
    }

    @Cacheable("findAllComments", cacheManager = "caffeineCacheManager")
    fun findAllComments(): List<Comment> {

        val comments = restTemplate.getForEntity(GET_COMMENTS_URL, Array<Comment>::class.java)
            .body?.toList()

        return comments ?: listOf()
    }

    @Cacheable("findAllUsers", cacheManager = "caffeineCacheManager")
    fun findAllUsers(): List<User> {

        val users = restTemplate.getForEntity(GET_USERS_URL, Array<User>::class.java)
            .body?.toList()

        return users ?: listOf()
    }
}