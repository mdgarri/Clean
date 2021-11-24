package extensions

import com.google.gson.Gson
import org.koin.core.context.GlobalContext

inline fun <reified T> fromJsonToObject(json: String?): T? {
    return try {
        if (json.isNullOrEmpty()) return null
        val gson = GlobalContext.get().get<Gson>()
        return gson.fromJson(json, T::class.java)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> fromObjectToJson(value: T?): String {
    return try {
        if (value == null) return ""
        val gson = GlobalContext.get().get<Gson>()
        return gson.toJson(value)
    } catch (e: Exception) {
        ""
    }
}