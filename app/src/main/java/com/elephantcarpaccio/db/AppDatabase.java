package com.elephantcarpaccio.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.db.dao.StateTaxDAO;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.StringUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;


@Database(entities = {Item.class, StateTax.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ItemDAO itemModel();

    public abstract StateTaxDAO stateTaxModel();

    private static Context context;

    public static AppDatabase getDatabase(Context ctx) {
        if (INSTANCE == null) {
            context = ctx;
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "pos_db")
                    .allowMainThreadQueries()
                    .addCallback(dbCallback)
                    .build();
        }
        return INSTANCE;
    }

    public static AppDatabase getMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .allowMainThreadQueries()
                    .addCallback(dbCallback)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            Executors.newSingleThreadScheduledExecutor().execute(() -> addStateTaxes(context));
        }
    };

    private static void addStateTaxes(Context ctx){
        List<StateTax> stateTaxList = null;
        try {
            String stateTaxes = StringUtils.readRawResource(ctx, R.raw.state_tax);
            stateTaxList = StateTax.createStateTaxList(stateTaxes);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        getDatabase(context).stateTaxModel().insertStateTaxList(stateTaxList);
    }
}