package com.example.cleanarchitecture.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Hwang on 2018-03-19.
 *
 * Description : 데이터 모델
 */
object Model {
    data class Root<out E> (val code: Int, val message: String, val data: E)
    @Parcelize @Entity(tableName = "user") data class User(@PrimaryKey var id: Int, var name: String, var age: Int, var nationality: String) : Parcelable {
        constructor() : this(0, "", 0, "")
    }
}