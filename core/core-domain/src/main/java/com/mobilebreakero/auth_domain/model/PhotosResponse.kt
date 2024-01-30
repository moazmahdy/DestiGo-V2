package com.mobilebreakero.auth_domain.model

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
	@field:SerializedName("data")
	val data: List<PhotoDataItem?> = emptyList()
)

data class Small(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class User(

	@field:SerializedName("username")
	val username: String? = null
)

data class Thumbnail(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Large(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Source(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null
)

data class Images(

	@field:SerializedName("small")
	val small: Small? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("original")
	val original: Original? = null,

	@field:SerializedName("large")
	val large: Large? = null,

	@field:SerializedName("medium")
	val medium: Medium? = null
)

data class PhotoDataItem(

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("is_blessed")
	val isBlessed: Boolean? = null,

	@field:SerializedName("album")
	val album: String? = null,

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("published_date")
	val publishedDate: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class Original(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Medium(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
