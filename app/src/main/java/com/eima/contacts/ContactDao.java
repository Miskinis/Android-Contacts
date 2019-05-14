package com.eima.contacts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT COUNT(*) from contact")
    int countContacts();

    @Insert
    void insertAll(Contact... contacts);

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);
}