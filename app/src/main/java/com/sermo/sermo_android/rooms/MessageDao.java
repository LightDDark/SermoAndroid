package com.sermo.sermo_android.rooms;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sermo.sermo_android.enteties.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message WHERE contactId = :contactId")
    List<Message> getAll(String contactId);

    @Query("DELETE FROM message WHERE contactId = :contactId")
    void clear(String contactId);

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);
}
