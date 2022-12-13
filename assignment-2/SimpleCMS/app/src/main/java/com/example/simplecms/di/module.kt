package com.example.simplecms.di

import com.example.simplecms.PostDetailViewModel
import com.example.simplecms.PostListViewModel
import com.example.simplecms.UserViewModel
import com.example.simplecms.network.RestService
import com.example.simplecms.util.AuthStorage
import com.example.simplecms.util.LocalDateTimeConverter
import com.example.simplecms.util.PostPagingSource
import com.example.simplecms.util.Toaster
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://mock")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<RestService> {
        get<Retrofit>().create(RestService::class.java)
    }

    single { AuthStorage(get()) }

    single { Toaster(get()) }

    single<Moshi> {
        Moshi.Builder()
            .add(LocalDateTimeConverter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    viewModel { UserViewModel(get(), get(), get()) }
    viewModel { PostListViewModel(get(), get(), get()) }
    viewModel { (postId: Int) -> PostDetailViewModel(postId, get(), get(), get()) }
    factory { PostPagingSource(get()) }
}