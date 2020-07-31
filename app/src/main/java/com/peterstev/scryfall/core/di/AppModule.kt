package com.peterstev.scryfall.core.di

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.peterstev.safebodachallengepeterslight.R
import com.peterstev.scryfall.data.repository.MainRepository
import com.peterstev.scryfall.data.repository.MockRepository
import com.peterstev.scryfall.domain.network.ApiHelper
import com.peterstev.scryfall.domain.network.ApiHelperImpl
import com.peterstev.scryfall.domain.network.ApiService
import com.peterstev.scryfall.presentation.viewmodels.FragmentViewModel
import com.peterstev.scryfall.presentation.viewmodels.MockViewModel
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesOkHTTPClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                ChuckInterceptor(application)
                    .showNotification(true)
                    .retainDataFor(ChuckInterceptor.Period.FOREVER)
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient, @ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .baseUrl(context.resources.getString(R.string.BASE_URL))
            .build()
    }

    @Provides
    fun providesRequestOptions(): RequestOptions {
        return RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    @Provides
    fun providesGlide(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application).setDefaultRequestOptions(requestOptions)
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper = apiHelperImpl

    @Provides
    fun providesViewModel(
        mainRepository: MainRepository
    ): FragmentViewModel {
        return FragmentViewModel(mainRepository)
    }

    @Provides
    fun providesMockViewModel(
        repository: MockRepository
    ): MockViewModel {
        return MockViewModel(repository)
    }
}