package com.keep_updated.covidhelper.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest  //For Unit testing
class ArticleDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDao


    @Before
    fun setup() {
        //Creating Database in memory(Ram) and running it on main thread
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getArticleDao()
    }

    @After
    fun tearDao() {
        database.close()
    }


    //----------------------------------------------Test Cases-------------------------------------------------------------------
    @Test
    fun insertArticle() = runBlockingTest {
        //Arrange
        val source = Source("id", "name")
        val article =
            Article(0, "author", "content", "desc", "publish", source, "title", "url", "img")

        //Act
        dao.insert(article)

        //Assert
        val allArticles = dao.getSavedNews().getOrAwaitValue()
        assertThat(allArticles).contains(article)
    }

    @Test
    fun deleteArticle() = runBlockingTest {
        //Arrange
        val source = Source("id", "name")
        val article =
            Article(0, "author", "content", "desc", "publish", source, "title", "url", "img")
        dao.insert(article)

        //Act
        dao.deleteSavedArticle(article)

        //Assert
        val allArticles = dao.getSavedNews().getOrAwaitValue()
        assertThat(allArticles).doesNotContain(article)
    }


}