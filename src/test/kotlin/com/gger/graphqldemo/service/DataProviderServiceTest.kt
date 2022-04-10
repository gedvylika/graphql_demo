package com.gger.graphqldemo.service

import com.gger.graphqldemo.model.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
internal class DataProviderServiceTest {
    @Mock
    private lateinit var restTemplate: RestTemplate

    @InjectMocks
    private lateinit var dataProviderService: DataProviderService

    private val testPost = Post(
        title = "post title",
        id = 1,
        body = "body",
        user = 1
    )

    private val testComment = Comment(
        body = "This is test comment",
        id = 1,
        email = "someone@somewhere.net",
        post = 1
    )

    private val testUser = User(
        id = 1,
        email = "someone@somewhere.net",
        address = Address(
            street = "Street name",
            suite = "Test Suite",
            city = "Somewhere climate is great",
            zipcode = "1564654654654-GC",
            geo = Geo(
                lat = "223322",
                lng = "322223"
            )
        ),
        company = Company(
            name = "Best company",
            catchPhrase = "Does it catches you?",
            bs = "Oh yeah, business"
        ),
        name = "John Smith, Jr",
        phone = "4654564654654",
        userName = "js456",
        website = "on.lt"
    )


    @Test
    fun findAllPostsFetchOk() {
        Mockito
            .`when`(restTemplate.getForEntity(DataProviderService.GET_POSTS_URL, Array<Post>::class.java))
            .thenReturn(ResponseEntity(arrayOf(testPost), HttpStatus.OK));

        val posts = dataProviderService.findAllPosts()

        assertEquals(1, posts.size)

        val fetchedPost = posts[0]

        assertEquals(testPost, fetchedPost)
    }

    @Test
    fun findAllPostsFailedToFetch() {
        Mockito
            .`when`(restTemplate.getForEntity(DataProviderService.GET_POSTS_URL, Array<Post>::class.java))
            .thenReturn(ResponseEntity(null, HttpStatus.BAD_REQUEST));
        val posts = dataProviderService.findAllPosts()
        assertEquals(0, posts.size)
    }

    @Test
    fun findAllComments() {
        Mockito
            .`when`(restTemplate.getForEntity(DataProviderService.GET_COMMENTS_URL, Array<Comment>::class.java))
            .thenReturn(ResponseEntity(arrayOf(testComment), HttpStatus.OK));

        val comments = dataProviderService.findAllComments()

        assertEquals(1, comments.size)

        val fetchedComment = comments[0]

        assertEquals(testComment, fetchedComment)
    }

    @Test
    fun findAllCommentsFailedToFetch() {
        Mockito
            .`when`(restTemplate.getForEntity(DataProviderService.GET_COMMENTS_URL, Array<Comment>::class.java))
            .thenReturn(ResponseEntity(null, HttpStatus.BAD_REQUEST));
        val comments = dataProviderService.findAllComments()
        assertEquals(0, comments.size)
    }

    @Test
    fun findAllUsers() {
        Mockito
            .`when`(restTemplate.getForEntity(DataProviderService.GET_USERS_URL, Array<User>::class.java))
            .thenReturn(ResponseEntity(arrayOf(testUser), HttpStatus.OK));

        val users = dataProviderService.findAllUsers()

        assertEquals(1, users.size)

        val fetchedUser = users[0]

        assertEquals(testUser, fetchedUser)

    }

    @Test
    fun findAllUsersFailedToFetch() {
        Mockito
            .`when`(restTemplate.getForEntity(DataProviderService.GET_USERS_URL, Array<User>::class.java))
            .thenReturn(ResponseEntity(null, HttpStatus.BAD_REQUEST));
        val users = dataProviderService.findAllUsers()
        assertEquals(0, users.size)
    }
}