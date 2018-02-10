package kshitij.com.acion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ujjwal on 06-02-2018.
 */

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Let's Start Cooking!");
        setContentView(R.layout.recipe_display);

        //get search value
        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("key_dishname");
        }

        Context mContext = getApplicationContext();
        SQLiteDatabase mDatabase = new DBHelper(mContext).getReadableDatabase();
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM appdb1 WHERE name LIKE \'"+value+"\';", null);
        mCursor.moveToFirst();

        TextView name = (TextView) findViewById(R.id.tvRecipeName);
        TextView difficulty = (TextView) findViewById(R.id.tvDifficulty);
        TextView time = (TextView) findViewById(R.id.tvTime);
        TextView serves = (TextView) findViewById(R.id.tvServes);
        TextView desc = (TextView) findViewById(R.id.tvDescription);
        TextView ingredient = (TextView) findViewById(R.id.tvIngredients);
        TextView procedure = (TextView) findViewById(R.id.tvProcedure);
        ImageView image = (ImageView) findViewById(R.id.ivRecipe);

        name.setText(mCursor.getString(0).toString());
        desc.setText(mCursor.getString(1).toString());
        ingredient.setText(mCursor.getString(2).toString());
        procedure.setText(mCursor.getString(3).toString());
        difficulty.setText(mCursor.getString(5).toString()+" / 5");
        time.setText(mCursor.getString(6).toString()+"min");
        serves.setText(mCursor.getString(7).toString());
        image.setImageResource(getResources().getIdentifier(""+mCursor.getString(8), "drawable", getPackageName()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(RecipeDisplay.this, AboutUs.class);
        startActivity(intent);
        return true;
    }
}

