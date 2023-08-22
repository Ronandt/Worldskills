package com.example.worldskills

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class SharedPrefResolver {
    companion object {
        fun getSession(context: Context): JSONObject? {
            println("AAAAA" + context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("session", JSONObject().toString()))
            return context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("session", JSONObject().toString())
                ?.let { JSONObject(it) }
        }

        fun setSession(string: JSONObject,context:Context) {
            println("HIHIHIHI" + string.toString())
            with(context.getSharedPreferences("User", Context.MODE_PRIVATE).edit()) {
                putString("session", string.toString())
                apply()
            }
        }

        fun getCart(context: Context, mobileNumber: String): JSONObject {
            if(context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("cart-${mobileNumber}", "cart-0") == "cart-0") {
                with(context.getSharedPreferences("User", Context.MODE_PRIVATE).edit()) {
                    this.putString("cart-${mobileNumber}", JSONObject().apply{
                        put("prefered_order", JSONArray())
                    }.toString())
                    apply()
                }
            }
           return JSONObject(context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("cart-${mobileNumber}", "cart-0"))
        }
        fun addCart(context: Context, mobileNumber: String, item: JSONObject) {
            var sharedpref = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            with(sharedpref.edit()) {
               var cart = getCart(context, mobileNumber)
                println("HOW" +  cart.toString())
                cart.getJSONArray("prefered_order").apply {
                    put(item)
                }

                println(cart.toString())
                putString("cart-${mobileNumber}", cart.toString())
                apply()
            }
           // return JSONObject(context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("cart-${mobileNumber}", "cart-0"))
        }

        fun clearCart(context: Context, mobileNumber:String) {
            with(context.getSharedPreferences("User", Context.MODE_PRIVATE).edit()) {
                remove("cart-${mobileNumber}")
                apply()
            }
        }

    }
}