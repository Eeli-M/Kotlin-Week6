package com.example.viikko1.data.repository

import com.example.viikko1.data.local.TaskDao
import com.example.viikko1.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val pendingTaskCount: Flow<Int> = taskDao.getPendingTaskCount()

    suspend fun insert(task: Task): Long {
        return taskDao.insert(task)
    }
    suspend fun update(task: Task) {
        taskDao.update(task)
    }
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
    suspend fun toggleTaskStatus(taskId: Int, completed: Boolean) {
        taskDao.updateTaskStatus(taskId, completed)
    }
    fun getTasksByStatus(completed: Boolean): Flow<List<Task>> {
        return taskDao.getTasksByStatus(completed)
    }
}