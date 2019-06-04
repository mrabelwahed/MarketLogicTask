package com.marketlogic.data

import java.io.Serializable

data class Article (
    val title:String,
    val description:String,
    val url:String,
    val urlToImage:String,
    val content:String,
    val publishedAt:String
    ):Serializable