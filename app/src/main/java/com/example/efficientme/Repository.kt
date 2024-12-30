package com.example.efficientme

class TasksRepository(private val tasksDao: TasksDao) {
    suspend fun insertTasks(task: Task) {
        tasksDao.insertTask(task)
    }

    suspend fun updateTasks(task: Task) {
        tasksDao.updateTask(task)
    }

    suspend fun deleteTasks(task: Task) {
        tasksDao.deleteTask(task)
    }

    suspend fun getTasks(): List<Task> {
        return tasksDao.getTasks()
    }
}

class ProfileRepository(private val profileDao: ProfileDao) {
    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    suspend fun getProfile(): Profile? {
        return profileDao.getProfile()
    }
}

class BreaksRepository(private val breaksDao: BreaksDao) {
    suspend fun insertBreaks(br: Break) {
        breaksDao.insertBreaks(br)
    }

    suspend fun updateBreaks(br: Break) {
        breaksDao.updateBreaks(br)
    }

    suspend fun deleteBreaks(br: Break) {
        breaksDao.deleteBreaks(br)
    }

    suspend fun getBreaks(): List<Break> {
        return breaksDao.getBreaks()
    }
}