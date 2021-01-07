package com.xebia.myapplication.repository

import com.xebia.myapplication.apiClient.FeedService
import com.xebia.myapplication.apiClient.RetrofitInstance
import com.xebia.myapplication.model.APIResult
import com.xebia.myapplication.model.Feed
import java.lang.Exception

class FeedDataRepository {

    private var rssFeedService: FeedService = RetrofitInstance.feedService

    suspend fun getImagesList(): APIResult<Feed> {
        return try {
            val response = rssFeedService.getImagesList()
            APIResult(APIResult.Status.SUCCESS, response, "")
        } catch (e: Exception) {
            APIResult(APIResult.Status.ERROR, null, e.message)
        }
    }
}