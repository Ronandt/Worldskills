package com.example.worldskills

object CalculateCost {
    fun findSizeCost(size: String): Float {
        return when(size) {
            "Medium" -> {
                0f
            }
            "Large" -> {
                3.5f
            }
            else -> {
               0f
            }

        }

    }

    fun flavourCost(flavour: String): Float {
        return when(flavour) {
            "Vanilla" -> {
                0f
            }
            "Chocolate" -> {
                1f
            }
            "Durian" -> {
                3f
            }
            else -> {
                0f
            }
        }
    }

    fun drinkCost(drink: String): Float {
        println(drink +" HUH")
        return when(drink) {

            getAllDrinks()[0] -> {
                7.3f
            }
            getAllDrinks()[1] -> {
                10f
            }
            getAllDrinks()[2] -> {
                8f
            }
            "Bubble Milk Tea with Ice cream!" -> {
                7.3f
            }
            else -> {
                0f
            }
        }
    }

    fun calculateTotalCost(quantity: Int, size: String, flavour: String, tea:  String): Float {

        return quantity * (findSizeCost(size) + flavourCost(flavour) + drinkCost(tea))
    }




}

fun getAllDrinks(): List<String> {
    return listOf("Bubble Milk Tea with Ice Cream!", "Red bull tea", "Lemon tea")
}