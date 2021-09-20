package com.sample.ru.features.article

import com.sample.ru.data.model.ArticleModel

sealed class ArticleState
class SuccessArticle(
    val article: ArticleModel
): ArticleState()
object EmptyArticle: ArticleState()