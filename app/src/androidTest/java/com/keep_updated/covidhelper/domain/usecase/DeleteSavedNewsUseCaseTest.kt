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
class DeleteSavedNewsUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var fakeNewsRepository: FakeNewsRepository
    private lateinit var deleteSavedNewsUseCase: DeleteSavedNewsUseCase


    @Before
    fun setup() {
        fakeNewsRepository = FakeNewsRepository()
        deleteSavedNewsUseCase = DeleteSavedNewsUseCase(fakeNewsRepository)
    }


    @Test
    fun deleteArticle_getZeroItems() = runBlockingTest {
        val source = Source("id", "name")
        val article =
            Article(0, "author", "content", "desc", "publish", source, "title", "url", "img")

        fakeNewsRepository.saveNews(article)
        deleteSavedNewsUseCase.execute(article)

        assertThat(fakeNewsRepository.getSavedNews().getOrAwaitValue().size).isEqualTo(0)
    }

    @Test
    fun deleteArticle_newAndOldArticleListUnequal() = runBlockingTest {
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
            fakeNewsRepository.saveNews(article)
            list.add(article)
        }

        deleteSavedNewsUseCase.execute(list[2])

        assertThat(fakeNewsRepository.getSavedNews().getOrAwaitValue()).isNotEqualTo(list)
    }

}