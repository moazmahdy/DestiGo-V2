package com.mobilebreakero.destigo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.data.repoimpl.FireStoreRepoImpl
import com.mobilebreakero.data.repoimpl.PostRepoImpl
import com.mobilebreakero.data.repoimpl.TripRepoImpl
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.PostsRepo
import com.mobilebreakero.domain.repo.TripsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun providesAuthRepository(
        auth: FirebaseAuth,
        repository: FireStoreRepository,
        fireStore: FirebaseFirestore
    ): AuthRepository =
        AuthRepositoryImpl(auth, repository, fireStore)

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