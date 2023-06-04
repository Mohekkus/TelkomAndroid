package com.telkom.capex.room.providers

import android.content.Context
import androidx.room.Room
import com.telkom.capex.room.BudgetDatabase
import com.telkom.capex.room.entity.BudgetListYearEntity
import com.telkom.capex.room.etc.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BudgetModule {

    @Provides
    @Singleton
    fun provideBudgetDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(
                context,
                BudgetDatabase::class.java,
                DatabaseReference.BUDGET_DATABASE
            )
            .build()

    @Provides
    @Singleton
    fun provideDAO(db: BudgetDatabase) = db.budgetDAO()
}