package com.nkot117.core.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText

class ApiClient(private val httpClient: HttpClient) {
    suspend fun get(url: String, block: HttpRequestBuilder.() -> Unit = {}): String =
        httpClient.get(url, block).bodyAsText()

    suspend fun post(url: String, block: HttpRequestBuilder.() -> Unit = {}): String =
        httpClient.post(url, block).bodyAsText()
}
