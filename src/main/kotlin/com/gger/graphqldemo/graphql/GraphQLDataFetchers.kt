package com.gger.graphqldemo.graphql

import com.gger.graphqldemo.model.Comment
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import com.gger.graphqldemo.model.Post
import com.gger.graphqldemo.model.User
import com.gger.graphqldemo.service.DataProviderService
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.List

@Component
class GraphQLDataFetchers(
    private val dataProviderService: DataProviderService
) {
    val postByIdDataFetcher: DataFetcher<Post>
        get() = DataFetcher { dataFetchingEnvironment: DataFetchingEnvironment ->
            val postId: Int = dataFetchingEnvironment.getArgument<String>("id").toInt()
            dataProviderService
                .findAllPosts()
                .firstOrNull { it.id == postId }
        }

    val postUserDataFetcher: DataFetcher<User>
        get() = DataFetcher { dataFetchingEnvironment: DataFetchingEnvironment ->
            val post: Post = dataFetchingEnvironment.getSource()
            val userId = post.user
            dataProviderService
                .findAllUsers()
                .firstOrNull { it.id == userId }
        }

    val postCommentsDataFetcher: DataFetcher<List<Comment>>
        get() = DataFetcher { dataFetchingEnvironment: DataFetchingEnvironment ->
            val post: Post = dataFetchingEnvironment.getSource()
            dataProviderService
                .findAllComments()
                .filter { it.post == post.id }
        }
}