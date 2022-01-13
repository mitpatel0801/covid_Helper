package com.keep_updated.covidhelper.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.keep_updated.covidhelper.data.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM Articles")
    fun getSavedNews(): LiveData<List<Article>>

    @Delete
    suspend fun deleteSavedArticle(article: Article)

}