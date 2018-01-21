package br.com.dcarv.arcmovies.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents countries and regions in the app
 *
 * @author Danilo Carvalho
 */
data class Country(
    val abbr: String,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(abbr)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString() = "$name ($abbr)"
}