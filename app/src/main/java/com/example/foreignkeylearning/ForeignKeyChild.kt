package com.example.foreignkeylearning

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ChildTable",
    foreignKeys = arrayOf(
        ForeignKey(entity = ParentModel::class,
        parentColumns = arrayOf("parentID"),
        childColumns = arrayOf("parentColumn"),
        onDelete = ForeignKey.CASCADE)
    )
)
data class ChildModel(
    @PrimaryKey(autoGenerate = true)
    val childID: Long = 0,

    @ColumnInfo(name = "childColumnOne")
    val childColumnOne: String,

    @ColumnInfo(name = "parentColumn")
    val parentID: Long
)