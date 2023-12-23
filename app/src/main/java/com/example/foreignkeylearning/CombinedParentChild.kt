package com.example.foreignkeylearning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.RoomDatabase
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CombinedParentChildModel(
    @Embedded
    val parentModel: ParentModel,

    @Relation(
        parentColumn = "parentID",
        entityColumn = "parentColumn"
    )
    val childModels: List<ChildModel>
)

@Dao
interface CombinedParentChildDAO {
    @Insert
    suspend fun save(parentInstance: ParentModel): Long

    @Insert
    suspend fun save(childInstance: ChildModel)

    // Observe the following two: they return everything that also includes the child
    @Transaction // Transaction ensures that the fetches happen together
    @Query("SELECT * FROM ParentTable")
    fun getAllFlow(): Flow<List<CombinedParentChildModel>>

    @Transaction
    @Query("SELECT * FROM ParentTable WHERE parentID = :id")
    suspend fun getByParentID(id: Long): CombinedParentChildModel


    @Transaction
    @Query("DELETE FROM ParentTable WHERE parentID = :parentId")
    suspend fun deleteParentByParentID(parentId: Long)


    @Transaction
    @Query("DELETE FROM ChildTable WHERE childID = :childID")
    suspend fun deleteChildByChildID(childID: Long)

}


@Database(
    entities = [ParentModel::class, ChildModel::class],
    version = 1
)
abstract class ParentAndChildDatabase: RoomDatabase(){
    abstract val CombinedParentChildDAO: CombinedParentChildDAO
}

class combinedParentChildViewModel(private val combinedParentChildDAO: CombinedParentChildDAO) : ViewModel() {

    private val _allParentsAndChildren = MutableStateFlow<List<CombinedParentChildModel>>(emptyList())
    val allParentsAndChildren: StateFlow<List<CombinedParentChildModel>> = _allParentsAndChildren
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        observeAllParentsAndChildren()
    }


    fun addParentAndChild() {
        viewModelScope.launch() {

            val parentId = combinedParentChildDAO.save(ParentModel(parentColumnOne = "Parent Column One Text"))
            // Add the first child
            combinedParentChildDAO.save(ChildModel(childColumnOne = "Child Column One Text", parentID = parentId))
            // Add the second child
            combinedParentChildDAO.save(ChildModel(childColumnOne = "Child Column One Text", parentID = parentId))

        }

    }

    fun deleteParent(parentId: Long) {
        viewModelScope.launch() {

            combinedParentChildDAO.deleteParentByParentID(parentId)


        }
    }



    fun deleteChildOnly(childID: Long) {
        viewModelScope.launch() {

            combinedParentChildDAO.deleteChildByChildID(childID)


        }
    }

    private fun observeAllParentsAndChildren() {
        viewModelScope.launch {
            combinedParentChildDAO.getAllFlow().collect {
                _allParentsAndChildren.value = it
            }
        }
    }

}
