package com.example.worldskills

import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

object  CallAPI{
    fun login(mobileNumber: String): JSONObject {
        var url = URL("http://10.0.2.2:3000/api/wss/userLogin")
        with(url.openConnection() as HttpURLConnection) {
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            var output = DataOutputStream(outputStream)
            output.write(JSONObject().apply {
                put("mobile", mobileNumber)
            }.toString().toByteArray())
            var stream = inputStream.bufferedReader()?.let { it.readText() }

            return JSONObject(stream)
        }
    }

    fun pay(cart: JSONObject, mobileNumber: String) {
        var url = URL("http://10.0.2.2:3000/api/wss/orderT")
        with(url.openConnection() as HttpURLConnection) {
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            var output = DataOutputStream(outputStream)
            output.write(cart.apply {
                put("mobile", mobileNumber)
            }.toString().toByteArray())
            var stream = inputStream.bufferedReader()?.let { it.readText() }
            println(stream)
        }
    }

    fun points(mobileNumber: String) {
        var url = URL("http://10.0.2.2:3000/api/wss/orderT")
        with(url.openConnection() as HttpURLConnection) {
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            var output = DataOutputStream(outputStream)
            /*output.write(cart.apply {
                put("mobile", mobileNumber)
            }.toString().toByteArray())*/
            var stream = inputStream.bufferedReader()?.let { it.readText() }
            println(stream)
        }
    }

}