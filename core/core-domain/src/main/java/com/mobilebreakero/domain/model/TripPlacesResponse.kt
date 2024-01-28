package com.mobilebreakero.domain.model

import com.google.gson.annotations.SerializedName

data class TripPlacesResponse(

	@field:SerializedName("data")
	val data: List<DataItem?> = emptyList()
)

data class AddressObj(

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

data class DataItem(

	@field:SerializedName("address_obj")
	val addressObj: AddressObj? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location_id")
	val locationId: String? = null

)