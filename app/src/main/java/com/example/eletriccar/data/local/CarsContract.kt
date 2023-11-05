package com.example.eletriccar.data.local

import android.provider.BaseColumns

object CarsContract {

    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POWER = "power"
        const val COLUMN_NAME_REFILL = "price"
        const val COLUMN_NAME_URL_PHOTO = "url_photo"
    }

    const val TABLE_CAR =
        "CREATE TABLE ${
            CarEntry.TABLE_NAME
        }(" +
                "${
                    BaseColumns._ID
                } INTEGER PRIMARY KEY," +
                "${
                    CarEntry.COLUMN_NAME_PRICE
                } TEXT," +
                "${
                    CarEntry.COLUMN_NAME_BATTERY
                } TEXT," +
                "${
                    CarEntry.COLUMN_NAME_POWER
                } TEXT," +
                "${
                    CarEntry.COLUMN_NAME_REFILL
                } TEXT," +
                "${
                    CarEntry.COLUMN_NAME_URL_PHOTO
                } TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXIST ${
            CarEntry.TABLE_NAME
        }"

}