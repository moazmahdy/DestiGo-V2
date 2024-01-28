package com.mobilebreakero.domain.model

import com.google.gson.annotations.SerializedName

data class DetailsResponse(

	@field:SerializedName("address_obj")
	val addressObj: AddressObject? = null,

	@field:SerializedName("amenities")
	val amenities: List<String?>? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("neighborhood_info")
	val neighborhoodInfo: List<Any?>? = null,

	@field:SerializedName("location_id")
	val locationId: String? = null,

	@field:SerializedName("trip_types")
	val tripTypes: List<TripTypesItem?>? = null,

	@field:SerializedName("write_review")
	val writeReview: String? = null,

	@field:SerializedName("ancestors")
	val ancestors: List<AncestorsItem?>? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("parent_brand")
	val parentBrand: String? = null,

	@field:SerializedName("review_rating_count")
	val reviewRatingCount: ReviewRatingCount? = null,

	@field:SerializedName("subratings")
	val subratings: Subratings? = null,

	@field:SerializedName("ranking_data")
	val rankingData: RankingData? = null,

	@field:SerializedName("photo_count")
	val photoCount: String? = null,

	@field:SerializedName("web_url")
	val webUrl: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("price_level")
	val priceLevel: String? = null,

	@field:SerializedName("awards")
	val awards: List<Any?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("num_reviews")
	val numReviews: String? = null,

	@field:SerializedName("styles")
	val styles: List<String?>? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("subcategory")
	val subcategory: List<SubcategoryItem?>? = null,

	@field:SerializedName("see_all_photos")
	val seeAllPhotos: String? = null
)

data class JsonMember2(

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class ReviewRatingCount(

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("2")
	val jsonMember2: String? = null,

	@field:SerializedName("3")
	val jsonMember3: String? = null,

	@field:SerializedName("4")
	val jsonMember4: String? = null,

	@field:SerializedName("5")
	val jsonMember5: String? = null
)

data class JsonMember3(

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class Subratings(

	@field:SerializedName("0")
	val jsonMember0: JsonMember0? = null,

	@field:SerializedName("1")
	val jsonMember1: JsonMember1? = null,

	@field:SerializedName("2")
	val jsonMember2: JsonMember2? = null,

	@field:SerializedName("3")
	val jsonMember3: JsonMember3? = null,

	@field:SerializedName("4")
	val jsonMember4: JsonMember4? = null,

	@field:SerializedName("5")
	val jsonMember5: JsonMember5? = null
)

data class AddressObject(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("address_string")
	val addressString: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("postalcode")
	val postalcode: String? = null,

	@field:SerializedName("street1")
	val street1: String? = null,

	@field:SerializedName("street2")
	val street2: String? = null,

	@field:SerializedName("state")
	val state: String? = null
)

data class SubcategoryItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class Category(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class JsonMember1(

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class AncestorsItem(

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location_id")
	val locationId: String? = null
)

data class JsonMember0(

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class JsonMember5(

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class TripTypesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class RankingData(

	@field:SerializedName("ranking_string")
	val rankingString: String? = null,

	@field:SerializedName("geo_location_id")
	val geoLocationId: String? = null,

	@field:SerializedName("ranking_out_of")
	val rankingOutOf: String? = null,

	@field:SerializedName("ranking")
	val ranking: String? = null,

	@field:SerializedName("geo_location_name")
	val geoLocationName: String? = null
)

data class JsonMember4(

	@field:SerializedName("rating_image_url")
	val ratingImageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)
