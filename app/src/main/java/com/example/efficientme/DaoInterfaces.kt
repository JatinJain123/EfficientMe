package com.example.efficientme

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("select * from Tasks order by deadline asc")
    suspend fun getTasks(): List<Task>
}

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("select * from Profile where profileId = 1")
    suspend fun getProfile(): Profile?
}

@Dao
interface BreaksDao {
    @Insert
    suspend fun insertBreaks(br: Break)

    @Update
    suspend fun updateBreaks(br: Break)

    @Delete
    suspend fun deleteBreaks(br: Break)

    @Query("select * from Breaks")
    suspend fun getBreaks(): List<Break>
}