package kshitij.com.acion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ujjwal on 06-02-2018.
 */

public class RandomResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Todays' Special");

        Context mContext = getApplicationContext();
        SQLiteDatabase mDatabase = new DBHelper(mContext).getWritableDatabase();
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM appdb1;", null);

        int randomNum = ThreadLocalRandom.current().nextInt(1, 10);
        mCursor.moveToPosition(randomNum);
        Intent intent = new Intent(RandomResult.this, RecipeDisplay.class);
        Bundle activityBundle = new Bundle();//Bundle used to send info across activities
        activityBundle.putString("key_dishname", mCursor.getString(0));
        intent.putExtras(activityBundle);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(RandomResult.this, AboutUs.class);
        startActivity(intent);
        return true;
    }
}
