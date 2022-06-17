package com.sermo.sermo_android.rooms;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sermo.sermo_android.enteties.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(String id);

    @Query("DELETE FROM contact")
    void clear();

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
}
