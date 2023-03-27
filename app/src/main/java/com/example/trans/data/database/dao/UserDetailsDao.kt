package com.example.trans.data.database.dao

import androidx.room.*
import com.example.trans.network.responses.UserDetails

@Dao
@Entity
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserDetails)


    @Query("SELECT * FROM user WHERE usrId = :usrId")
    fun getUser(usrId: String): UserDetails

    @Query("DELETE FROM user")
    fun nukeConfigurable()

}