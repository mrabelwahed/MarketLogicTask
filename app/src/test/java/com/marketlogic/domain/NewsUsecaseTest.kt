package com.marketlogic.domain

import com.marketlogic.data.Article
import com.marketlogic.data.NewsResponse
import com.marketlogic.repository.NewsRepository
import com.marketlogic.rules.RxSchedulerRule
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class NewsUsecaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var repository: NewsRepository

    @Mock
    lateinit var newsResponse: Flowable<NewsResponse>

    lateinit var newsUsecase: NewsUsecase

    @Before
    fun setup() {
       newsUsecase = NewsUsecase(repository)
    }

    @Test
    fun `when news requested successfully we should return news response `(){
        val articles = ArrayList<Article>()
        articles.add(Article("Apple replaces bash with zsh as the default shell in macOS Catalina - The Verge",
            "Apple is moving away from the bash shell on macOS. The new macOS Catalina update will include zsh as the default shell, and older macOS versions can also move to the new shell too",
            "https://www.theverge.com/2019/6/4/18651872/apple-macos-catalina-zsh-bash-shell-replacement-features",
           "https://cdn.vox-cdn.com/thumbor/rpo7HvNgQzDU5RNFAcoyoLXFZRE=/0x124:1782x1057/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/16318574/twarren_bashmacos_1.jpg",
            "(Reuters) — The U.S. government is gearing up to investigate whether Amazon, Apple, Facebook and Google misuse their massive market power, sources told Reuters on Monday, setting up what could be an unprecedented, wide-ranging probe of some of the world’s lar… [+5365 chars]",
            "2019-06-04T08:27:34Z"))
        articles.add(Article("Apple replaces bash with zsh as the default shell in macOS Catalina - The Verge",
            "Apple is moving away from the bash shell on macOS. The new macOS Catalina update will include zsh as the default shell, and older macOS versions can also move to the new shell too",
            "https://www.theverge.com/2019/6/4/18651872/apple-macos-catalina-zsh-bash-shell-replacement-features",
           "https://cdn.vox-cdn.com/thumbor/rpo7HvNgQzDU5RNFAcoyoLXFZRE=/0x124:1782x1057/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/16318574/twarren_bashmacos_1.jpg",
            "(Reuters) — The U.S. government is gearing up to investigate whether Amazon, Apple, Facebook and Google misuse their massive market power, sources told Reuters on Monday, setting up what could be an unprecedented, wide-ranging probe of some of the world’s lar… [+5365 chars]",
            "2019-06-04T08:27:34Z"))
        articles.add(Article("Apple replaces bash with zsh as the default shell in macOS Catalina - The Verge",
            "Apple is moving away from the bash shell on macOS. The new macOS Catalina update will include zsh as the default shell, and older macOS versions can also move to the new shell too",
            "https://www.theverge.com/2019/6/4/18651872/apple-macos-catalina-zsh-bash-shell-replacement-features",
           "https://cdn.vox-cdn.com/thumbor/rpo7HvNgQzDU5RNFAcoyoLXFZRE=/0x124:1782x1057/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/16318574/twarren_bashmacos_1.jpg",
            "(Reuters) — The U.S. government is gearing up to investigate whether Amazon, Apple, Facebook and Google misuse their massive market power, sources told Reuters on Monday, setting up what could be an unprecedented, wide-ranging probe of some of the world’s lar… [+5365 chars]",
            "2019-06-04T08:27:34Z"))
        val newsResponse = NewsResponse("ok",articles)

        Mockito.`when`(repository.getNewsList(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )).thenReturn(Flowable.just(newsResponse))

        val result = newsUsecase.getNewsList(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )


        val testSubscriber = TestSubscriber<NewsResponse>()
        result.subscribe(testSubscriber)
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)

        val list = testSubscriber.values()
        Assert.assertThat(list.size, `is`(1))
        Assert.assertThat(list[0].status, `is`("ok"))
        Assert.assertThat(list[0].articles.size, `is`(3))

    }
}