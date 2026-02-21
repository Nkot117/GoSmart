package com.nkot117.core.data.di

import android.content.Context
import androidx.room.Room
import com.nkot117.core.data.db.AppDatabase
import com.nkot117.core.data.db.dao.ItemsDao
import com.nkot117.core.data.db.dao.SpecialItemDateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "smart_go.db")
        .build()

    @Provides
    fun provideItemsDao(db: AppDatabase): ItemsDao = db.itemsDao()

    @Provides
    fun provideSpecialItemDatesDao(db: AppDatabase): SpecialItemDateDao = db.specialItemDateDao()

    @Provides
    fun provideDailyNoteDao(db: AppDatabase) = db.dailyNoteDao()
}
