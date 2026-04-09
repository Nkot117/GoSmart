package com.nkot117.core.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

class KtorHttpClientFactory {
    fun create(): HttpClient = HttpClient(OkHttp)
}
