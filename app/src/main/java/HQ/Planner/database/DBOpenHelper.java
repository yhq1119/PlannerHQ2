package HQ.Planner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {


    final static String DATABASE_NAME = "planner.db";
    final static int DATABASE_VERSION = 1;
    final static SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;


    public DBOpenHelper(Context context) {
        super(
                context,
                DATABASE_NAME,
                DATABASE_FACTORY,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
