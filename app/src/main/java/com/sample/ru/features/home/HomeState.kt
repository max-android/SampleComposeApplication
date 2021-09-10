package com.sample.ru.features.home

import com.sample.ru.data.model.FoodModel
import com.sample.ru.data.model.ArticleModel
import com.sample.ru.data.model.MemModel
import com.sample.ru.data.model.PhotoModel

sealed class HomeState
object LoadingHome: HomeState()
class SuccessHome(
    val memes: List<MemModel>,
    val articleModels: List<ArticleModel>,
    val photoModels: List<PhotoModel>,
    val foods: List<FoodModel>,
    val thematic: List<String>
): HomeState()
class ErrorHome(val error: Throwable): HomeState()