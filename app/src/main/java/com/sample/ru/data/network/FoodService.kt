package com.sample.ru.data.network

import com.sample.ru.data.model.FoodModel

class FoodService {

    val foodImages = listOf(
        FoodModel(butter, "Butter"),
        FoodModel(dessert, "Dessert"),
        FoodModel(butter2, "Butter2"),
        FoodModel(dosa, "Dosa"),
        FoodModel(idly, "Idly"),
        FoodModel(pasta, "Pasta"),
        FoodModel(pizza, "Pizza"),
        FoodModel(rice, "Rice"),
        FoodModel(samosa, "Samosa")
    )

    companion object {
        private const val butter = "https://foodish-api.herokuapp.com/images/butter-chicken/butter-chicken8.jpg"
        private const val dessert = "https://foodish-api.herokuapp.com/images/dessert/dessert16.jpg"
        private const val butter2 = "https://foodish-api.herokuapp.com/images/butter-chicken/butter-chicken3.jpg"
        private const val dosa = "https://foodish-api.herokuapp.com/images/dosa/dosa20.jpg"
        private const val idly = "https://foodish-api.herokuapp.com/images/idly/idly6.jpg"
        private const val pasta = "https://foodish-api.herokuapp.com/images/pasta/pasta16.jpg"
        private const val pizza = "https://foodish-api.herokuapp.com/images/pizza/pizza91.jpg"
        private const val rice = "https://foodish-api.herokuapp.com/images/rice/rice5.jpg"
        private const val samosa = "https://foodish-api.herokuapp.com/images/samosa/samosa11.jpg"
    }
}