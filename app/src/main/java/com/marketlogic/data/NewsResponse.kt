package com.marketlogic.data

import java.io.Serializable

data class NewsResponse(val status:String,val articles: ArrayList<Article>):Serializable