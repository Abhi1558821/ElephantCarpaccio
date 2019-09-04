package com.elephantcarpaccio.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elephantcarpaccio.model.StateTax;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface StateTaxDAO {
    @Query("SELECT * FROM state_tax")
    LiveData<List<StateTax>> getAllStateTaxes();

    @Query("SELECT * FROM state_tax")
    List<StateTax> getAllStateTax();

    @Query("SELECT * FROM state_tax where state == :stateName")
    StateTax getStateTaxFromState(String stateName);

    @Query("SELECT state FROM state_tax")
    List<String> getStatesList();

    @Insert(onConflict = IGNORE)
    long insertStateTax(StateTax stateTax);

    @Insert
    void insertStateTaxList(List<StateTax> stateTaxList);

    @Update
    int updateStateTax(StateTax stateTax);

    @Update
    void updateStateTaxList(List<StateTax> stateTaxes);

    @Delete
    void deleteStateTax(StateTax stateTax);

    @Query("DELETE FROM state_tax")
    void deleteAll();

}
