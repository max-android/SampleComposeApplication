package com.sample.ru.features.news

import com.sample.ru.data.model.BaseModel

sealed class ListNewsState
class SuccessListNews(
    val news: List<BaseModel>
): ListNewsState()
object EmptyListNews: ListNewsState()