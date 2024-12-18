package com.mogun.shoppingapp.di

import com.mogun.data.repository.AccountRepositoryImpl
import com.mogun.data.repository.BasketRepositoryImpl
import com.mogun.data.repository.CategoryRepositoryImpl
import com.mogun.data.repository.LikeRepositoryImpl
import com.mogun.data.repository.MainRepositoryImpl
import com.mogun.data.repository.ProductDetailRepositoryImpl
import com.mogun.data.repository.PurchaseHistoryRepositoryImpl
import com.mogun.data.repository.SearchRepositoryImpl
import com.mogun.data.repository.TempRepositoryImpl
import com.mogun.domain.repository.AccountRepository
import com.mogun.domain.repository.BasketRepository
import com.mogun.domain.repository.CategoryRepository
import com.mogun.domain.repository.LikeRepository
import com.mogun.domain.repository.MainRepository
import com.mogun.domain.repository.ProductDetailRepository
import com.mogun.domain.repository.PurchaseHistoryRepository
import com.mogun.domain.repository.SearchRepository
import com.mogun.domain.repository.TempRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindTempRepository(tempRepositoryImpl: TempRepositoryImpl): TempRepository

    @Binds
    @Singleton
    fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindProductDetailRepository(productDetailRepositoryImpl: ProductDetailRepositoryImpl): ProductDetailRepository

    @Binds
    @Singleton
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindLikeRepository(likeRepositoryImpl: LikeRepositoryImpl): LikeRepository

    @Binds
    @Singleton
    fun bindBasketRepository(basketRepositoryImpl: BasketRepositoryImpl): BasketRepository

    @Binds
    @Singleton
    fun bindPurchaseHistoryRepository(purchaseHistoryRepositoryImpl: PurchaseHistoryRepositoryImpl): PurchaseHistoryRepository
}