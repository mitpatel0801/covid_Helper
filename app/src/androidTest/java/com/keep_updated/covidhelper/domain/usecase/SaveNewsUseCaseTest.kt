package com.keep_updated.covidhelper.domain.usecase


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.Source
import com.keep_updated.covidhelper.domain.FakeNewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SaveNewsUseCaseTest {


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var fakeNewsRepository: FakeNewsRepository
    private lateinit var saveNewsUseCase: SaveNewsUseCase

    @Before
    fun setup() {
        fakeNewsRepository = FakeNewsRepository()
        saveNewsUseCase = SaveNewsUseCase(fakeNewsRepository)
    }

    @Test
    fun saveArticlesSuccessfully() = runBlockingTest {
        val list = mutableListOf<Article>()
        for (i in 1..5) {
            val source = Source("id$i", "name$i")
            val article =
                Article(
                    i,
                    "author$i",
                    "content$i",
                    "desc$i",
                    "publish$i",
                    source,
                    "title$i",
                    "url$i",
                    "img$i"
                )
            saveNewsUseCase.execute(article)
            list.add(article)
        }

        assertThat(fakeNewsRepository.getSavedNews().getOrAwaitValue()).isEqualTo(list)

    }

}