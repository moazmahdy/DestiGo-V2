package com.mobilebreakero.destigo.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mobilebreakero.auth_data.remote.TripApi
import com.mobilebreakero.auth_data.repoimpl.DetailsRepoImplementation
import com.mobilebreakero.auth_data.repoimpl.PhotoRepoImplementation
import com.mobilebreakero.auth_data.repoimpl.RecommededImple
import com.mobilebreakero.auth_data.repoimpl.SearchPlacesRepoImpl
import com.mobilebreakero.auth_domain.repo.AuthRepository
import com.mobilebreakero.auth_domain.repo.DetailsRepository
import com.mobilebreakero.auth_domain.repo.FireStoreRepository
import com.mobilebreakero.auth_domain.repo.PhotoRepository
import com.mobilebreakero.auth_domain.repo.PostsRepo
import com.mobilebreakero.auth_domain.repo.RecommendedTrips
import com.mobilebreakero.auth_domain.repo.SearchResultRepo
import com.mobilebreakero.auth_domain.repo.TripsRepo
import com.mobilebreakero.auth_domain.usecase.DetailsUseCase
import com.mobilebreakero.auth_domain.usecase.GetPublicTripsUseCase
import com.mobilebreakero.auth_domain.usecase.GetReviewsUseCase
import com.mobilebreakero.auth_domain.usecase.PhotoUseCase
import com.mobilebreakero.auth_domain.usecase.RecommendedPlaceUseCase
import com.mobilebreakero.auth_domain.usecase.RecommendedUseCase
import com.mobilebreakero.auth_domain.usecase.SearchPlacesUseCase
import com.mobilebreakero.auth_domain.usecase.UpdatePublicTripDate
import com.mobilebreakero.auth_domain.usecase.UpdatePublicTripDays
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.usecase.CheckUserSignedInUseCase
import com.mobilebreakero.auth_domain.usecase.CurrentUser
import com.mobilebreakero.auth_domain.usecase.DeleteAccount
import com.mobilebreakero.auth_domain.usecase.GetAuthState
import com.mobilebreakero.auth_domain.usecase.ReloadUser
import com.mobilebreakero.auth_domain.usecase.RestPassword
import com.mobilebreakero.auth_domain.usecase.SendEmailVerification
import com.mobilebreakero.auth_domain.usecase.SendPasswordResetEmail
import com.mobilebreakero.auth_domain.usecase.SignInAnnonymously
import com.mobilebreakero.auth_domain.usecase.SignInWithEmailAndPassword
import com.mobilebreakero.auth_domain.usecase.SignOut
import com.mobilebreakero.auth_domain.usecase.SignUpWithEmailAndPassword
import com.mobilebreakero.auth_domain.usecase.UpdateEmail
import com.mobilebreakero.auth_domain.usecase.UpdatePassword
import com.mobilebreakero.auth_domain.usecase.firestore.GetSavedPlaces
import com.mobilebreakero.auth_domain.usecase.firestore.GetSavedTrips
import com.mobilebreakero.auth_domain.usecase.firestore.IsTripFinished
import com.mobilebreakero.auth_domain.usecase.firestore.user.AddUser
import com.mobilebreakero.auth_domain.usecase.firestore.UserUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.user.GetUserById
import com.mobilebreakero.auth_domain.usecase.firestore.user.GetUsers
import com.mobilebreakero.auth_domain.usecase.firestore.user.UpdateLocation
import com.mobilebreakero.auth_domain.usecase.firestore.user.UpdateProfilePhoto
import com.mobilebreakero.auth_domain.usecase.firestore.user.UpdateStatus
import com.mobilebreakero.auth_domain.usecase.firestore.user.GetInterestedPlaces
import com.mobilebreakero.auth_domain.usecase.firestore.user.UpdateUser
import com.mobilebreakero.auth_domain.usecase.firestore.user.UpdateInterestedPlaces
import com.mobilebreakero.auth_domain.usecase.firestore.post.AddCommentUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.AddPostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.DeletePostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.GetPostsById
import com.mobilebreakero.auth_domain.usecase.firestore.post.GetPostDetails
import com.mobilebreakero.auth_domain.usecase.firestore.post.GetPostsUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.LikePostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.PostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.SharePostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.trips.AddChickList
import com.mobilebreakero.auth_domain.usecase.firestore.trips.AddPlaceVisitDate
import com.mobilebreakero.auth_domain.usecase.firestore.trips.AddPlaces
import com.mobilebreakero.auth_domain.usecase.firestore.trips.AddPublicTrips
import com.mobilebreakero.auth_domain.usecase.firestore.trips.AddTrip
import com.mobilebreakero.auth_domain.usecase.firestore.trips.AddTripJournal
import com.mobilebreakero.auth_domain.usecase.firestore.trips.GetTrips
import com.mobilebreakero.auth_domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.user.UpdateUserSaved
import com.mobilebreakero.auth_domain.usecase.firestore.trips.UpdatePhoto
import com.mobilebreakero.auth_domain.usecase.firestore.trips.DeleteTrip
import com.mobilebreakero.auth_domain.usecase.firestore.trips.GetPublicTrips
import com.mobilebreakero.auth_domain.usecase.firestore.trips.GetTripDetails
import com.mobilebreakero.auth_domain.usecase.firestore.trips.GetTripsByCategories
import com.mobilebreakero.auth_domain.usecase.firestore.trips.IsPlaceVisited
import com.mobilebreakero.auth_domain.usecase.firestore.trips.UpdatePlacePhoto
import com.mobilebreakero.auth_domain.usecase.firestore.trips.UpdateTripDate
import com.mobilebreakero.auth_domain.usecase.firestore.trips.UpdateTripDays
import com.mobilebreakero.auth_domain.usecase.firestore.trips.UpdateTripName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFireStoreUseCases(
        repo: FireStoreRepository
    ) = UserUseCase(
        addUser = AddUser(repo),
        getUsers = GetUsers(repo),
        updateUser = UpdateUser(repo),
        getUserByID = GetUserById(repo),
        updateUserLocation = UpdateLocation(repo),
        updateUserPhotoUrl = UpdateProfilePhoto(repo),
        updateUserStatus = UpdateStatus(repo),
        updateUserInterestedPlaces = UpdateInterestedPlaces(repo),
        getInterestedPlaces = GetInterestedPlaces(repo),
        updateUserSaved = UpdateUserSaved(repo),
        getSavedPlaces = GetSavedPlaces(repo),
        getSavedTrips = GetSavedTrips(repo),
    )

    @Provides
    fun provideTripsUseCases(
        repo: TripsRepo
    ) = TripsUseCase(
        getTrips = GetTrips(repo),
        addTrip = AddTrip(repo),
        chickList = AddChickList(repo),
        places = AddPlaces(repo),
        deleteTrip = DeleteTrip(repo),
        updatePhoto = UpdatePhoto(repo),
        getTripDetails = GetTripDetails(repo),
        getTripsByCategories = GetTripsByCategories(repo),
        addPlaceVisitDate = AddPlaceVisitDate(repo),
        updatePlacePhoto = UpdatePlacePhoto(repo),
        isVisited = IsPlaceVisited(repo),
        addTripJournal = AddTripJournal(repo),
        savePublicTrips = AddPublicTrips(repo),
        getPublicTrips = GetPublicTrips(repo),
        updateTripDate = UpdateTripDate(repo),
        updateTripDays = UpdateTripDays(repo),
        updateTripName = UpdateTripName(repo),
        isTripFinished = IsTripFinished(repo)
    )

    @Provides
    fun providePostUseCase(
        repo: PostsRepo
    ) = PostUseCase(
        addPost = AddPostUseCase(repo = repo),
        getPosts = GetPostsUseCase(repo),
        likePost = LikePostUseCase(repo),
        sharePost = SharePostUseCase(repo),
        addComment = AddCommentUseCase(repo),
        deletePost = DeletePostUseCase(repo),
        getPostsByUserId = GetPostsById(repo),
        getPostDetails = GetPostDetails(repo)
    )

    @Provides
    fun provideSearchPlacesRepo(api: TripApi): SearchResultRepo {
        return SearchPlacesRepoImpl(api)
    }

    @Provides
    fun provideSearchPlacesUseCase(repo: SearchResultRepo): SearchPlacesUseCase {
        return SearchPlacesUseCase(repo)
    }

    @Provides
    fun provideDetailsUseCase(repo: DetailsRepository): DetailsUseCase {
        return DetailsUseCase(repo)
    }

    @Provides
    fun provideDetailsRepository(api: TripApi, context: Context): DetailsRepository {
        return DetailsRepoImplementation(api, context)
    }

    @Provides
    fun providePhotosUseCase(repo: PhotoRepository): PhotoUseCase {
        return PhotoUseCase(repo)
    }

    @Provides
    fun providePhotosRepository(api: TripApi): PhotoRepository {
        return PhotoRepoImplementation(api)
    }

    @Provides
    fun provideRecomendationUseCase(repo: RecommendedTrips): RecommendedUseCase {
        return RecommendedUseCase(repo)
    }


    @Provides
    fun provideRecomendationPlacesUseCase(repo: RecommendedTrips): RecommendedPlaceUseCase {
        return RecommendedPlaceUseCase(repo)
    }

    @Provides
    fun provideGetPublicTrips(repo: RecommendedTrips): GetPublicTripsUseCase {
        return GetPublicTripsUseCase(repo)
    }

    @Provides
    fun provideRecomdedRepo(context: Context): RecommendedTrips {
        return RecommededImple(context)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideUpdateDatePUseCase(repo: RecommendedTrips): UpdatePublicTripDate {
        return UpdatePublicTripDate(repo)
    }

    @Provides
    fun provideGetReviewsUseCase(repo: DetailsRepository): GetReviewsUseCase {
        return GetReviewsUseCase(repo)
    }

    @Provides
    fun provideUpdateDaysPUseCase(repo: RecommendedTrips): UpdatePublicTripDays {
        return UpdatePublicTripDays(repo)
    }
}