package com.mobilebreakero.navigation_core

import javax.inject.Singleton

@Singleton
object NavigationRoutes {

    const val WELCOME_SCREEN = "Welcome"
    const val SIGN_IN_SCREEN = "Login"
    const val SIGN_UP_SCREEN = "SignUp"
    const val START_SCREEN = "StartAuth"
    const val HOME_SCREEN = "Home"
    const val PROFILE_SCREEN = "Profile"
    const val INTERESTED_PLACES_SCREEN = "InterestedPlaces"
    const val TRIPS_SCREEN = "Trips"
    const val CREATE_TRIP = "CreateTrip"
    const val PLAN_CHECK_LIST = "planCheckList/{tripId}"
    const val SCAN_SCREEN = "Scan"
    const val EMAIL_VERIFICATION_SCREEN = "EmailVerification"
    const val SEND_CONFIRMATION_CODE = "SendConfirmationCode"
    const val SEND_FORGET_PASSWORD_EMAIL = "Forget Password Sent"
    const val CHOOSE_NEW_PASSWORD = "ChooseNewPassword"
    const val PASSWORD_UPDATED_SUCCESSFULLY = "PasswordUpdated"
    const val CONFIRM_CODE_SENT = "ConfirmCode"
    const val SEARCH_SCREEN = "Search"
    const val ADD_POST_SCREEN = "AddPost"
    const val YOUR_POSTS_SCREEN = "YourPosts"
    const val SAVED_SCREEN = "Saved"
    const val YOUR_TRIPS_SCREEN = "YourTrips"
    const val ACCOUNT_SCREEN = "Account"
    const val PROFILE_SETTINGS = "YourProfile"
    const val ACCOUNT_ACCESS_SETTINGS = "AccountAccessSettings"
    const val CHOOSE_NEW_EMAIL = "UpdateYourEmail"
    const val SIGN_IN_BEFORE_UPDATE = "SignInBeforeUpdate"
    const val EMAIL_SENT_SUCCESSFULY = "EmailSentSuccessfully"
    const val CHOOSE_NEW_USERNAME = "UpdateUserName"
    const val DETAILS_SCREEN = "details/{locationId}"
    const val PLACES_DETAILS_SCREEN = "savePlacesDetails/{locationId}/{tripId}"
    const val ADD_PLACES_SCREEN = "addPlaces/{tripId}"
    const val CHOOSE_COVER_SCREEN = "chooseCover/{tripId}"
    const val ADD_COMMENT = "comment/{postId}"
    const val PROFILE_DETAILS = "profileDetails/{userId}"
    const val POSTS_DETAILS = "postDetails/{postId}"
    const val TRIP_DETAILS = "tripDetails/{tripId}"
    const val ADD_JOURNAL = "addTripJournal/{tripId}"
    const val TRIP_JOURNAL_DETAILS = "JournalDetails/{tripId}"
    const val PUBLIC_TRIP_DETAILS = "publicTripDetails/{tripId}"

}