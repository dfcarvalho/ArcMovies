package br.com.dcarv.arcmovies.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents languages in the app
 *
 * @author Danilo Carvalho
 */
data class Language(
    val code: String,
    val name: String,
    val englishName: String,
    var translationCode: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(englishName)
        parcel.writeString(translationCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Language> {
        override fun createFromParcel(parcel: Parcel): Language {
            return Language(parcel)
        }

        override fun newArray(size: Int): Array<Language?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return if (name.isBlank()) {
            englishName
        } else {
            "$name - $englishName ($translationCode)"
        }
    }
}