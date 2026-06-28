package com.example.data

import org.json.JSONArray
import org.json.JSONObject

object JsonUtils {
    fun toJson(items: List<BillItem>): String {
        return try {
            val jsonArray = JSONArray()
            for (item in items) {
                val jsonObject = JSONObject().apply {
                    put("nameEn", item.nameEn)
                    put("nameEs", item.nameEs)
                    put("quantity", item.quantity)
                    put("unitPrice", item.unitPrice)
                }
                jsonArray.put(jsonObject)
            }
            jsonArray.toString()
        } catch (e: Exception) {
            "[]"
        }
    }

    fun fromJson(json: String): List<BillItem> {
        return try {
            val list = mutableListOf<BillItem>()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(
                    BillItem(
                        nameEn = jsonObject.optString("nameEn", ""),
                        nameEs = jsonObject.optString("nameEs", ""),
                        quantity = jsonObject.optInt("quantity", 1),
                        unitPrice = jsonObject.optDouble("unitPrice", 0.0)
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
}
