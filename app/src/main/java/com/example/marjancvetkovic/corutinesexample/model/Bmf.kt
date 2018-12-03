package com.example.marjancvetkovic.corutinesexample.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Bmf(
    @PrimaryKey
    @SerializedName("DisKz")
    @Expose
    var id: String,
    @SerializedName("DisNameLang")
    @Expose
    var name: String?,
    @SerializedName("DisPlz")
    @Expose
    var zip: Int = 0,
    @SerializedName("DisOrt")
    @Expose
    var city: String?,
    @SerializedName("DisStrasse")
    @Expose
    var street: String?,
    @SerializedName("DisTel")
    @Expose
    var phone: String?,
    @ColumnInfo(name = "opening_hours")
    @SerializedName("DisOeffnung")
    @Expose
    var openingHours: String?,
    @ColumnInfo(name = "image_url")
    @SerializedName("DisFotoUrl")
    @Expose
    var imageUrl: String?,
    @SerializedName("DisLatitude")
    @Expose
    var latitude: String?,
    @SerializedName("DisLongitude")
    @Expose
    var longitude: String
) : Parcelable