package com.example.viikko1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viikko1.data.repository.TaskRepository
import com.example.viikko1.data.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask = _selectedTask.asStateFlow()

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun dismissDialog() {
        _selectedTask.value = null
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
            dismissDialog()
        }
    }
    val allTasks: StateFlow<List<Task>> = repository.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val pendingCount: StateFlow<Int> = repository.pendingTaskCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description
            )
            repository.insert(task)
        }
    }
    fun toggleTask(task: Task) {
        viewModelScope.launch {
            val updated = task.copy(isCompleted = !task.isCompleted)
            repository.update(updated)
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch{
            repository.delete(task)
        }
    }
}
