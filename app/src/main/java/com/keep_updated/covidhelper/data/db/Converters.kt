package com.keep_updated.covidhelper.data.db

import androidx.room.TypeConverter
import com.keep_updated.covidhelper.data.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(source: String): Source {
        return Source(source, source)
    }
}