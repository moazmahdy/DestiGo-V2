package com.mobilebreakero.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobilebreakero.addpost.AddPostScreen
import com.mobilebreakero.auth.ui.login.components.SendForgetPassword
import com.mobilebreakero.auth.ui.login.screens.LoginScreen
import com.mobilebreakero.auth.ui.signup.screens.SignUpScreen
import com.mobilebreakero.auth.ui.start.screen.StartAuthScreen
import com.mobilebreakero.auth.ui.verification.EmailVerificationScreen
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ACCOUNT_ACCESS_SETTINGS
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ACCOUNT_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ADD_COMMENT
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ADD_JOURNAL
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ADD_PLACES_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ADD_POST_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CHOOSE_NEW_EMAIL
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CHOOSE_NEW_PASSWORD
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CHOOSE_NEW_USERNAME
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CONFIRM_CODE_SENT
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CREATE_TRIP
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.DETAILS_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_SENT_SUCCESSFULY
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_VERIFICATION_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.INTERESTED_PLACES_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PASSWORD_UPDATED_SUCCESSFULLY
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PLACES_DETAILS_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PLAN_CHECK_LIST
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.POSTS_DETAILS
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_DETAILS
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_SETTINGS
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PUBLIC_TRIP_DETAILS
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SAVED_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SEARCH_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SEND_CONFIRMATION_CODE
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SEND_FORGET_PASSWORD_EMAIL
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_IN_BEFORE_UPDATE
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_UP_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.START_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIP_DETAILS
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.WELCOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.YOUR_POSTS_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.YOUR_TRIPS_SCREEN
import com.mobilebreakero.details.DetailsScreen
import com.mobilebreakero.details.PlacesDetailsTrips
import com.mobilebreakero.details.TripDetailsScreen
import com.mobilebreakero.details.components.AddTripJournal
import com.mobilebreakero.details.publicTrips.PublicTripDetails
import com.mobilebreakero.home.AddCommentScreen
import com.mobilebreakero.home.HomeScreen
import com.mobilebreakero.home.PostDetailsScreen
import com.mobilebreakero.home.ProfileDetailsScreen
import com.mobilebreakero.interestedplaces.screen.InterestedPlacesScreen
import com.mobilebreakero.profile.account.AccountSettings
import com.mobilebreakero.profile.account.ProfileSettingsScreen
import com.mobilebreakero.profile.account.accountacess.AccountAccessSettingsScreen
import com.mobilebreakero.profile.account.accountacess.SignInBeforeUpdatingYourInformation
import com.mobilebreakero.profile.account.accountacess.components.PasswordUpdatedSuccessfullyScreen
import com.mobilebreakero.profile.account.accountacess.updateEmail.ChooseNewEmail
import com.mobilebreakero.profile.account.accountacess.updateEmail.EmailUpdateSentSuccessfully
import com.mobilebreakero.profile.account.accountacess.updatepassword.screens.ChooseNewPasswordScreen
import com.mobilebreakero.profile.account.accountacess.updatepassword.screens.ConfirmTheConfirmationCodeScreen
import com.mobilebreakero.profile.account.accountacess.updatepassword.screens.SendConfirmationCodeScreen
import com.mobilebreakero.profile.account.accountacess.updateusername.ChooseNewUserName
import com.mobilebreakero.profile.screen.ProfileScreen
import com.mobilebreakero.profile.screen.SavedScreen
import com.mobilebreakero.profile.screen.YourPostsScreen
import com.mobilebreakero.profile.screen.YourTripsScreen
import com.mobilebreakero.scan.ScanScreen
import com.mobilebreakero.search.screen.SearchScreen
import com.mobilebreakero.trips.screens.addplaces.AddPlacesScreen
import com.mobilebreakero.trips.screens.plan.CreateTripScreen
import com.mobilebreakero.trips.screens.plan.PlanScreen
import com.mobilebreakero.trips.screens.planchecklist.PlanCheckListScreen
import com.mobilebreakero.welcome.WelcomeScreen

private const val TransitionDuration = 600

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: String,
) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(TransitionDuration)
            )
        },
        exitTransition = { fadeOut(tween(TransitionDuration)) },
        popEnterTransition = { fadeIn(tween(TransitionDuration)) },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(TransitionDuration)
            )
        }
    ) {
        composable(route = WELCOME_SCREEN) {
            WelcomeScreen(navController = navController)
        }
        composable(route = START_SCREEN) {
            StartAuthScreen(navController = navController)
        }
        composable(route = SEND_FORGET_PASSWORD_EMAIL) {
            SendForgetPassword(navController = navController)
        }
        composable(route = EMAIL_VERIFICATION_SCREEN) {
            EmailVerificationScreen(navController = navController)
        }
        composable(route = SIGN_UP_SCREEN) {
            SignUpScreen(navController = navController)
        }
        composable(route = SIGN_IN_SCREEN) {
            LoginScreen(navController = navController)
        }
        composable(route = HOME_SCREEN) {
            HomeScreen(navController = navController)
        }

        composable(route = INTERESTED_PLACES_SCREEN) {
            InterestedPlacesScreen(navController = navController)
        }
        composable(route = SCAN_SCREEN) {
            ScanScreen(navController = navController)
        }
        composable(route = PROFILE_SCREEN) {
            ProfileScreen(navController = navController)
        }
        composable(route = SEND_CONFIRMATION_CODE) {
            SendConfirmationCodeScreen(navController = navController)
        }
        composable(route = CHOOSE_NEW_PASSWORD) {
            ChooseNewPasswordScreen(navController = navController)
        }
        composable(route = PASSWORD_UPDATED_SUCCESSFULLY) {
            PasswordUpdatedSuccessfullyScreen(navController = navController)
        }
        composable(route = ADD_COMMENT) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId", "")
            if (postId != null) {
                AddCommentScreen(navController = navController, postId = postId)
            }
        }
        composable(route = POSTS_DETAILS) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId", "")
            if (postId != null) {
                PostDetailsScreen(navController = navController, postId = postId)
            }
        }

        composable(route = CONFIRM_CODE_SENT) {
            ConfirmTheConfirmationCodeScreen(navController = navController)
        }
        composable(route = SEARCH_SCREEN) {
            SearchScreen(navController = navController)
        }
        composable(route = TRIPS_SCREEN) {
            PlanScreen(navController = navController)
        }
        composable(route = CREATE_TRIP) {
            CreateTripScreen(navController = navController)
        }
        composable(route = PLAN_CHECK_LIST) {
            val tripId = it.arguments?.getString("tripId", "")
            val lastScreenRoute = navController.previousBackStackEntry?.destination?.route
            if (tripId != null) {
                if (lastScreenRoute != null)
                    PlanCheckListScreen(
                        navController = navController,
                        tripId = tripId,
                        screenRoot = lastScreenRoute
                    )
            }
        }
        composable(route = ADD_POST_SCREEN) {
            AddPostScreen(navController = navController)
        }
        composable(route = ACCOUNT_SCREEN) {
            AccountSettings(navController = navController)
        }
        composable(route = YOUR_POSTS_SCREEN) {
            YourPostsScreen(navController = navController)
        }
        composable(route = YOUR_TRIPS_SCREEN) {
            YourTripsScreen(navController = navController)
        }
        composable(route = ADD_PLACES_SCREEN) {
            val tripId = it.arguments?.getString("tripId", "")
            val lastScreenRoute = navController.previousBackStackEntry?.destination?.route
            if (tripId != null) {
                if (lastScreenRoute != null) {
                    AddPlacesScreen(
                        navController = navController,
                        tripId = tripId,
                        screenRoot = lastScreenRoute
                    )
                }
            }
        }
        composable(route = SAVED_SCREEN) {
            SavedScreen(navController = navController)
        }
        composable(route = PROFILE_SETTINGS) {
            ProfileSettingsScreen()
        }
        composable(route = ACCOUNT_ACCESS_SETTINGS) {
            AccountAccessSettingsScreen(navController = navController)
        }
        composable(route = CHOOSE_NEW_EMAIL) {
            ChooseNewEmail(navController = navController)
        }
        composable(route = SIGN_IN_BEFORE_UPDATE) {
            SignInBeforeUpdatingYourInformation(navController = navController)
        }
        composable(route = EMAIL_SENT_SUCCESSFULY) {
            EmailUpdateSentSuccessfully(navController = navController)
        }
        composable(route = CHOOSE_NEW_USERNAME) {
            ChooseNewUserName(navController = navController)
        }
        composable(route = DETAILS_SCREEN) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString("locationId")
            if (locationId != null) {
                DetailsScreen(locationId)
            }
        }
        composable(route = TRIP_DETAILS) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId", "")
            TripDetailsScreen(tripId ?: "", navController = navController)

        }
        composable(route = TRIP_DETAILS) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId", "")
            TripDetailsScreen(tripId ?: "", navController = navController)

        }
        composable(route = ADD_JOURNAL) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId", "")
            AddTripJournal(tripId = tripId ?: "", navController = navController)
        }
        composable(route = PUBLIC_TRIP_DETAILS) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId", "")
            PublicTripDetails(tripId = tripId ?: "", navController = navController)
        }
        composable(route = PLACES_DETAILS_SCREEN) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString("locationId", "")
            val tripId = backStackEntry.arguments?.getString("tripId", "")
            PlacesDetailsTrips(tripId = tripId ?: "", locationId = locationId ?: "")
        }
        composable(route = PROFILE_DETAILS) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId", "")
            ProfileDetailsScreen(navController = navController, userID = userId)
        }
    }
}