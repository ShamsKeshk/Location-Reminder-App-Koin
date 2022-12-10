package com.udacity.project4.locationreminders.framework.local.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.udacity.project4.locationreminders.framework.local.database.RemindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class LocalTestingModule {

    @Provides
    @Named("RemindersTestingDatabase")
    fun provideRoomForTesting(@ApplicationContext context : Context): RemindersDatabase{
        return Room.inMemoryDatabaseBuilder(
            context,
            RemindersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

}





