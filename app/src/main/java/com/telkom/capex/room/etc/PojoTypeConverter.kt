package com.telkom.capex.room.etc

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListJsonItem
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem

class PojoTypeConverter {

    @TypeConverter
    fun fromResultJson(json: String): BudgetListResultItem {
        // Convert the JSON string to a BudgetListResultItem object
        return Gson().fromJson(json, BudgetListResultItem::class.java)
    }

    @TypeConverter
    fun toResultJson(item: BudgetListResultItem): String {
        // Convert the BudgetListResultItem object to a JSON string
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromDataJson(json: String): BudgetListDataEntity {
        // Convert the JSON string to a BudgetListResultItem object
        return Gson().fromJson(json, BudgetListDataEntity::class.java)
    }

    @TypeConverter
    fun toDataJson(item: BudgetListDataEntity): String {
        // Convert the BudgetListResultItem object to a JSON string
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromJsonList(json: String): List<BudgetListJsonItem> {
        val listType = object : TypeToken<List<BudgetListJsonItem>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJsonList(list: List<BudgetListJsonItem>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromPairList(pairList: List<Pair<String, Double>>): String {
        val stringBuilder = StringBuilder()
        for ((first, second) in pairList) {
            stringBuilder.append("$first:$second,")
        }
        return stringBuilder.toString().dropLast(1)
    }

    @TypeConverter
    fun toPairList(pairListString: String): List<Pair<String, Double>> {
        val pairList = mutableListOf<Pair<String, Double>>()
        val pairs = pairListString.split(",")
        for (pair in pairs) {
            val keyValue = pair.split(":")
            val first = keyValue[0]
            val second = keyValue[1].toDouble()
            pairList.add(Pair(first, second))
        }
        return pairList
    }
}