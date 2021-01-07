package com.xebia.myapplication.apiClient

import com.xebia.myapplication.model.Feed
import retrofit2.http.GET

interface FeedService {
    @GET("feeds/photos_public.gne")
    suspend fun getImagesList(): Feed
}