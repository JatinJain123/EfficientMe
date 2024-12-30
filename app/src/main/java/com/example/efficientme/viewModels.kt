package com.example.efficientme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class LoadState<T>(
    val loading: Boolean = true,
    val data: T? = null,
    val error: String? = null,
)

class TasksViewModelFactory(
    private val tasksRepository: TasksRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksViewModel(tasksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TasksViewModel(private val tasksRepository: TasksRepository): ViewModel() {
    private val _status = mutableStateOf(LoadState<List<Task>>())
    val status: MutableState<LoadState<List<Task>>> = _status

    fun getTasks() {
        _status.value = LoadState(loading = true)
        viewModelScope.launch {
            try {
                val list: List<Task> = tasksRepository.getTasks()
                _status.value = LoadState(loading = false, data = list)
            } catch(e: Exception) {
                _status.value = LoadState(loading = false, error = "error fetching data: ${e.message}")
            }
        }
    }

    fun insertTasks(task: Task) {
        viewModelScope.launch {
            tasksRepository.insertTasks(task)
            getTasks()
        }
    }

    fun updateTasks(task: Task) {
        viewModelScope.launch {
            tasksRepository.updateTasks(task)
            getTasks()
        }
    }

    fun deleteTasks(task: Task) {
        viewModelScope.launch {
            tasksRepository.deleteTasks(task)
            getTasks()
        }
    }
}



class ProfileViewModelFactory(
    private val profileRepository: ProfileRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ProfileViewModel(private val profileRepository: ProfileRepository): ViewModel() {
    private val _status = mutableStateOf(LoadState<Profile>())
    val status: MutableState<LoadState<Profile>> = _status

    fun getProfile() {
        _status.value = LoadState(loading = true)
        viewModelScope.launch {
            try {
                val profile: Profile? = profileRepository.getProfile()
                _status.value = LoadState(loading = false, data = profile)
            } catch(e: Exception) {
                _status.value = LoadState(loading = false, error = "error fetching profile: ${e.message}")
            }
        }
    }

    fun insertProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.insertProfile(profile)
            getProfile()
        }
    }

    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.deleteProfile(profile)
            getProfile()
        }
    }
}



class BreaksViewModelFactory(
    private val breaksRepository: BreaksRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreaksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreaksViewModel(breaksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class BreaksViewModel(private val breaksRepository: BreaksRepository): ViewModel() {
    private val _status = mutableStateOf(LoadState<List<Break>>())
    val status: MutableState<LoadState<List<Break>>> = _status

    fun getBreaks() {
        _status.value = LoadState(loading = true)
        viewModelScope.launch {
            try {
                val list: List<Break> = breaksRepository.getBreaks()
                _status.value = LoadState(loading = false, data = list)
            } catch(e: Exception) {
                _status.value = LoadState(loading = false, error = "error fetching data: ${e.message}")
            }
        }
    }

    fun insertBreaks(br: Break) {
        viewModelScope.launch {
            breaksRepository.insertBreaks(br)
            getBreaks()
        }
    }

    fun updateBreaks(br: Break) {
        viewModelScope.launch {
            breaksRepository.updateBreaks(br)
            getBreaks()
        }
    }

    fun deleteBreaks(br: Break) {
        viewModelScope.launch {
            breaksRepository.deleteBreaks(br)
            getBreaks()
        }
    }
}