package com.gger.graphqldemo

import com.gger.graphqldemo.graphql.GraphQLDataFetchers
import com.gger.graphqldemo.model.*
import com.gger.graphqldemo.service.DataProviderService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
class IntegrationTest {

    @MockBean
    lateinit var dataProviderService: DataProviderService

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var graphQLDataFetchers: GraphQLDataFetchers

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(graphQLDataFetchers)


        Mockito.`when`(dataProviderService.findAllPosts()).thenReturn(listOf(testPost))
        Mockito.`when`(dataProviderService.findAllUsers()).thenReturn(listOf(testUser))
        Mockito.`when`(dataProviderService.findAllComments()).thenReturn(listOf(testComment))
    }


    @Test
    fun endpointIsAvailable() {
        val query = """{"query": 
            "{ 
               postById(id: 1){
                    id
                    title
                    body
                    comments {
                        body
                        email
                        id
                    }
                    user {
                        id
                        name
                        email
                        address {
                            street
                            city
                            geo {
                                lat
                                lng
                            }
                        }
                        company {
                            name
                        }
                    }
              } 
                
            }"
                
            }""".replace("\n", "").trimIndent()

        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        val entity = HttpEntity<String>(query, headers)
        val result:String = restTemplate.postForObject("/graphql", entity, String::class.java)

        assertEquals("{\"data\":{\"postById\":{\"id\":\"1\",\"title\":\"post title\",\"body\":\"body\",\"comments\":[{\"body\":\"This is test comment\",\"email\":\"someone@somewhere.net\",\"id\":\"1\"}],\"user\":{\"id\":\"1\",\"name\":\"John Smith, Jr\",\"email\":\"someone@somewhere.net\",\"address\":{\"street\":\"Street name\",\"city\":\"Somewhere climate is great\",\"geo\":{\"lat\":\"223322\",\"lng\":\"322223\"}},\"company\":{\"name\":\"Best company\"}}}}}", result)

    }

    private companion object {
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
    }
}