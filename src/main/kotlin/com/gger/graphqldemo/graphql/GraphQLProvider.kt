package com.gger.graphqldemo.graphql

import com.google.common.base.Charsets
import com.google.common.io.Resources
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.IOException
import javax.annotation.PostConstruct


@Component
class GraphQLProvider {
    @Autowired
    lateinit var graphQLDataFetchers: GraphQLDataFetchers
    private lateinit var graphQL: GraphQL

    @PostConstruct
    @Throws(IOException::class)
    fun init() {
        val url = Resources.getResource("schema.graphqls")
        val sdl = Resources.toString(url, Charsets.UTF_8)
        val graphQLSchema = buildSchema(sdl)
        graphQL = GraphQL.newGraphQL(graphQLSchema).build()
    }

    private fun buildSchema(sdl: String): GraphQLSchema {
        val typeRegistry = SchemaParser().parse(sdl)
        val runtimeWiring = buildWiring()
        val schemaGenerator = SchemaGenerator()
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)
    }

    private fun buildWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
            .type(
                TypeRuntimeWiring.newTypeWiring("Query")
                    .dataFetcher("postById", graphQLDataFetchers.postByIdDataFetcher)
            )
            .type(
                TypeRuntimeWiring.newTypeWiring("Post")
                    .dataFetcher("user", graphQLDataFetchers.postUserDataFetcher)
            )
            .type(
                TypeRuntimeWiring.newTypeWiring("Post")
                    .dataFetcher("comments", graphQLDataFetchers.postCommentsDataFetcher)
            )

            .build()
    }

    @Bean
    fun graphQL(): GraphQL {
        return graphQL
    }
}