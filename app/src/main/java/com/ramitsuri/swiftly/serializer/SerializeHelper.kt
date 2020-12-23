package com.ramitsuri.swiftly.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.math.BigDecimal
import java.text.ParseException

class BigDecimalAdapter() :
    JsonDeserializer<BigDecimal> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BigDecimal {
        try {
            json?.let {
                return BigDecimal(json.asString)
            }
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }
        return BigDecimal.ZERO
    }

}