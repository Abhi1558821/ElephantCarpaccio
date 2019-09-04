package com.elephantcarpaccio.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elephantcarpaccio.model.Item;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ItemDAO {

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAllItem();

    @Query("SELECT * FROM item")
    List<Item> getAllItems();

    @Insert(onConflict = IGNORE)
    long insertItem(Item item);

    @Update
    int updateItem(Item item);

    @Update
    void updateItem(List<Item> items);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM item")
    void deleteAll();

    @Query("SELECT * FROM item WHERE id=:id")
    Item getItemFromId(long id);
}
