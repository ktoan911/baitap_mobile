package com.example.student_app.data

import android.provider.BaseColumns

object StudentContract {
    object StudentEntry : BaseColumns {
        const val TABLE_NAME = "students"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_ADDRESS = "address"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${StudentEntry.TABLE_NAME} (" +
                "${StudentEntry.COLUMN_ID} TEXT PRIMARY KEY," +
                "${StudentEntry.COLUMN_NAME} TEXT," +
                "${StudentEntry.COLUMN_PHONE} TEXT," +
                "${StudentEntry.COLUMN_ADDRESS} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${StudentEntry.TABLE_NAME}"
}
