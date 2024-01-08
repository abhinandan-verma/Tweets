package com.abhicoding.tweets.repository

import androidx.compose.runtime.MutableState
import com.abhicoding.tweets.api.TweetsAPI
import com.abhicoding.tweets.model.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class TweetRepository @Inject constructor(private val tweetsAPI: TweetsAPI){

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: MutableStateFlow<List<String>>
        get() = _categories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: MutableStateFlow<List<TweetListItem>>
        get() = _tweets

    suspend fun getCategories(){
        val response = tweetsAPI.getCategories()
        if (response.isSuccessful && response.body() != null){
            _categories.emit(response.body()!!)
        }
    }
    suspend fun getTweets(category: String){
        val response = tweetsAPI.getTweets("tweets[?(@.category==\"$category\")]")
        if (response.isSuccessful && response.body() != null){
           _tweets.emit(response.body()!!)
        }
    }
}