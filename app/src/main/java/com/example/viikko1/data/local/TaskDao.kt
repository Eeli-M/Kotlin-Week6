package com.example.viikko1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.viikko1.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Insert
    suspend fun  insertAll(vararg tasks: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query("SELECT * FROM tasks WHERE is_completed = :completed")
    fun getTasksByStatus(completed: Boolean): Flow<List<Task>>

    @Update
    suspend fun update(task: Task)

    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Int, completed: Boolean)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteById(taskId: Int)

    @Query("SELECT COUNT(*) FROM tasks WHERE is_completed = 0")
    fun getPendingTaskCount(): Flow<Int>
}