package com.mobilebreakero.destigo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.auth_data.repoimpl.FireStoreRepoImpl
import com.mobilebreakero.auth_data.repoimpl.PostRepoImpl
import com.mobilebreakero.auth_data.repoimpl.TripRepoImpl
import com.mobilebreakero.auth_data.repository.AuthRepositoryImpl
import com.mobilebreakero.auth_domain.repo.AuthRepository
import com.mobilebreakero.auth_domain.repo.FireStoreRepository
import com.mobilebreakero.auth_domain.repo.PostsRepo
import com.mobilebreakero.auth_domain.repo.TripsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {



    @Provides
    fun providesFireStoreRepository(): FireStoreRepository =
        FireStoreRepoImpl()

    @Provides
    fun providePostRepo(): PostsRepo =
        PostRepoImpl()

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideTripsRepo(): TripsRepo {
        return TripRepoImpl()
    }


}