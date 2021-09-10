package com.sample.ru.data.repository

import com.sample.ru.data.network.*
import com.sample.ru.features.home.ErrorHome
import com.sample.ru.features.home.HomeState
import com.sample.ru.features.home.SuccessHome
import com.sample.ru.util.getBodyDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class HomeRepository(
    private val memesRestService: MemesRestService,
    private val newsRestService: NewsRestService,
    private val photoRestService: PhotoRestService,
    private val foodService: FoodService,
    private val thematicService: ThematicService
) {

    suspend fun loadHomeData(): HomeState {
        //TODO
        Timber.tag("--LOG-21").i("-----loadHomeData()")
        return try {
            coroutineScope {
                val memesDeferred = async { memesRestService.memes() }
                val articlesDeferred = async { newsRestService.articles() }
                val photosDeferred = async { photoRestService.photos() }
                val memes = memesDeferred.await().getBodyDto().memes
                val articles = articlesDeferred.await().getBodyDto()
                val photos = photosDeferred.await().getBodyDto()
                SuccessHome(
                    memes, articles, photos, foodService.foodImages, thematicService.thematic
                )
            }
        } catch (throwable: Throwable) {
            ErrorHome(throwable)
        }
    }
}