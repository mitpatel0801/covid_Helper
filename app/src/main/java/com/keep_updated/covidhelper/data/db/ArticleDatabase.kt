package com.keep_updated.covidhelper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.keep_updated.covidhelper.data.models.Article


@Database(
    entities = [Article::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}