package com.example.foreignkeylearning

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ParentTable")
data class ParentModel(
    @PrimaryKey(autoGenerate = true)
    val parentID: Long = 0,

    @ColumnInfo(name = "parentColumnOne")
    val parentColumnOne: String,
)
