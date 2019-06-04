package com.marketlogic.repository

import com.marketlogic.data.NewsResponse
import com.marketlogic.network.NewsApi
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class NewsRepositoryTest {

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()!!

    @Mock
    lateinit var service: NewsApi

    @Mock
    lateinit var newsResponse: Flowable<NewsResponse>

    lateinit var repository: NewsRepository

    @Before
    fun setup() {
        repository = NewsRepository(service)
    }

    @Test
    fun `when getNewsList is called should the retrofit service should be called and return response`() {
        Mockito.`when`(repository.getNewsList(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt())).thenReturn(newsResponse)

        val result = repository.getNewsList(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )

        assertThat(result, `is`(newsResponse))

        Mockito.verify(service).getNewsList(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt()
        )

        Mockito.verifyNoMoreInteractions(service)
    }
}