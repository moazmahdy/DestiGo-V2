package com.mobilebreakero.destigo.di

import android.content.Context
import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.data.repoimpl.DetailsRepoImplementation
import com.mobilebreakero.data.repoimpl.PhotoRepoImplementation
import com.mobilebreakero.data.repoimpl.RecommededImple
import com.mobilebreakero.data.repoimpl.SearchPlacesRepoImpl
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.DetailsRepository
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.PhotoRepository
import com.mobilebreakero.domain.repo.PostsRepo
import com.mobilebreakero.domain.repo.RecommendedTrips
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.usecase.DetailsUseCase
import com.mobilebreakero.domain.usecase.GetPublicTripsUseCase
import com.mobilebreakero.domain.usecase.GetReviewsUseCase
import com.mobilebreakero.domain.usecase.PhotoUseCase
import com.mobilebreakero.domain.usecase.RecommendedPlaceUseCase
import com.mobilebreakero.domain.usecase.RecommendedUseCase
import com.mobilebreakero.domain.usecase.SearchPlacesUseCase
import com.mobilebreakero.domain.usecase.UpdatePublicTripDate
import com.mobilebreakero.domain.usecase.UpdatePublicTripDays
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.usecase.auth.CheckUserSignedInUseCase
import com.mobilebreakero.domain.usecase.auth.CurrentUser
import com.mobilebreakero.domain.usecase.auth.DeleteAccount
import com.mobilebreakero.domain.usecase.auth.GetAuthState
import com.mobilebreakero.domain.usecase.auth.ReloadUser
import com.mobilebreakero.domain.usecase.auth.RestPassword
import com.mobilebreakero.domain.usecase.auth.SendEmailVerification
import com.mobilebreakero.domain.usecase.auth.SendPasswordResetEmail
import com.mobilebreakero.domain.usecase.auth.SignInAnnonymously
import com.mobilebreakero.domain.usecase.auth.SignInWithEmailAndPassword
import com.mobilebreakero.domain.usecase.auth.SignOut
import com.mobilebreakero.domain.usecase.auth.SignUpWithEmailAndPassword
import com.mobilebreakero.domain.usecase.auth.UpdateEmail
import com.mobilebreakero.domain.usecase.auth.UpdatePassword
import com.mobilebreakero.domain.usecase.firestore.GetSavedPlaces
import com.mobilebreakero.domain.usecase.firestore.GetSavedTrips
import com.mobilebreakero.domain.usecase.firestore.IsTripFinished
import com.mobilebreakero.domain.usecase.firestore.user.AddUser
import com.mobilebreakero.domain.usecase.firestore.UserUseCase
import com.mobilebreakero.domain.usecase.firestore.user.GetUserById
import com.mobilebreakero.domain.usecase.firestore.user.GetUsers
import com.mobilebreakero.domain.usecase.firestore.user.UpdateLocation
import com.mobilebreakero.domain.usecase.firestore.user.UpdateProfilePhoto
import com.mobilebreakero.domain.usecase.firestore.user.UpdateStatus
import com.mobilebreakero.domain.usecase.firestore.user.GetInterestedPlaces
import com.mobilebreakero.domain.usecase.firestore.user.UpdateUser
import com.mobilebreakero.domain.usecase.firestore.user.UpdateInterestedPlaces
import com.mobilebreakero.domain.usecase.firestore.post.AddCommentUseCase
import com.mobilebreakero.domain.usecase.firestore.post.AddPostUseCase
import com.mobilebreakero.domain.usecase.firestore.post.DeletePostUseCase
import com.mobilebreakero.domain.usecase.firestore.post.GetPostsById
import com.mobilebreakero.domain.usecase.firestore.post.GetPostDetails
import com.mobilebreakero.domain.usecase.firestore.post.GetPostsUseCase
import com.mobilebreakero.domain.usecase.firestore.post.LikePostUseCase
import com.mobilebreakero.domain.usecase.firestore.PostUseCase
import com.mobilebreakero.domain.usecase.firestore.post.SharePostUseCase
import com.mobilebreakero.domain.usecase.firestore.trips.AddChickList
import com.mobilebreakero.domain.usecase.firestore.trips.AddPlaceVisitDate
import com.mobilebreakero.domain.usecase.firestore.trips.AddPlaces
import com.mobilebreakero.domain.usecase.firestore.trips.AddPublicTrips
import com.mobilebreakero.domain.usecase.firestore.trips.AddTrip
import com.mobilebreakero.domain.usecase.firestore.trips.AddTripJournal
import com.mobilebreakero.domain.usecase.firestore.trips.GetTrips
import com.mobilebreakero.domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.domain.usecase.firestore.user.UpdateUserSaved
import com.mobilebreakero.domain.usecase.firestore.trips.UpdatePhoto
import com.mobilebreakero.domain.usecase.firestore.trips.DeleteTrip
import com.mobilebreakero.domain.usecase.firestore.trips.GetPublicTrips
import com.mobilebreakero.domain.usecase.firestore.trips.GetTripDetails
import com.mobilebreakero.domain.usecase.firestore.trips.GetTripsByCategories
import com.mobilebreakero.domain.usecase.firestore.trips.IsPlaceVisited
import com.mobilebreakero.domain.usecase.firestore.trips.UpdatePlacePhoto
import com.mobilebreakero.domain.usecase.firestore.trips.UpdateTripDate
import com.mobilebreakero.domain.usecase.firestore.trips.UpdateTripDays
import com.mobilebreakero.domain.usecase.firestore.trips.UpdateTripName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthUseCases(
        repo: AuthRepository
    ) = AuthUseCase(
        getAuthState = GetAuthState(repo),
        signInWithEmailAndPassword = SignInWithEmailAndPassword(repo),
        signUpWithEmailAndPassword = SignUpWithEmailAndPassword(repo),
        signOut = SignOut(repo),
        SignInAnnonymously = SignInAnnonymously(repo),
        sendEmailVerification = SendEmailVerification(repo),
        sendPasswordResetEmail = SendPasswordResetEmail(repo),
        currentUser = CurrentUser(repo),
        reloadUser = ReloadUser(repo),
        resetPassword = RestPassword(repo),
        updatePassword = UpdatePassword(repo),
        updateEmail = UpdateEmail(repo),
        checkUserSignedIn = CheckUserSignedInUseCase(repo),
        deleteAccount = DeleteAccount(repo)
    )

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
    fun provideGetReviewsUseCase(repo: DetailsRepository): GetReviewsUseCase{
        return GetReviewsUseCase(repo)
    }

    @Provides
    fun provideUpdateDaysPUseCase(repo: RecommendedTrips): UpdatePublicTripDays {
        return UpdatePublicTripDays(repo)
    }
}