package kshitij.com.acion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ujjwal on 06-02-2018.
 */

public class IngredientSearchResult extends AppCompatActivity {

    String[] ingredients = null;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Results");
        setContentView(R.layout.list_result);
        layout = (LinearLayout) findViewById(R.id.grid_view);

        //get search value
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ingredients = extras.getStringArray("list");
        }

        try {
            Context mContext = getApplicationContext();
            SQLiteDatabase mDatabase = new DBHelper(mContext).getReadableDatabase();

            Cursor mCursor = mDatabase.rawQuery("SELECT * FROM appdb0;", null);
            mCursor.moveToFirst();

            findMatch(mCursor);

        } catch (RuntimeException e) {
            Toast.makeText(IngredientSearchResult.this, ""+e, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(IngredientSearchResult.this, IngredientSearch.class);
            startActivity(intent);
        }
    }

    public void findMatch(Cursor mCursor) {
        int flag = 0, rowCount = 0;

        for (int i = 0, j = mCursor.getCount(); i < j; i++) {
            String[] iRequired = (mCursor.getString(1).toString().toLowerCase()).split(",");

            for (int k = 0, l = iRequired.length; k < l; k++) {
                flag = 2;
                for (int m = 0, n = ingredients.length; m < n; m++) {
                    if (!iRequired[k].trim().equals(ingredients[m].trim())) {
                        flag = 0;Log.i("na", "not found"); }
                    else {
                        flag = 1;Log.i("na", "found");
                        break;
                    }
                }

                if (flag == 0)
                    break;
                }

                if (flag != 0) {
                    getRow(mCursor.getString(0).toString());
                    rowCount++;
                    Log.i("na", "found");
                }
            mCursor.moveToNext();
        }
        if (rowCount == 0)
            emptyResult();
    }

    public void emptyResult() {
        LinearLayout addView = new LinearLayout(IngredientSearchResult.this);
        addView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.addView(addView);
        TextView textView = new TextView(this);
        textView.setText("No matches were found");
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(textView);
    }

    public void getRow(String str) {
        Context mContext = getApplicationContext();
        SQLiteDatabase mDatabase = new DBHelper(mContext).getReadableDatabase();
        addRow(mDatabase.rawQuery("SELECT * FROM appdb1 WHERE name LIKE \'"+str+"\';", null));
    }

    public void addRow(Cursor mCursor) {

        mCursor.moveToFirst();
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.list_result_row, null);
        TextView recipeName = (TextView) addView.findViewById(R.id.tvRecipeName);
        TextView difficulty = (TextView) addView.findViewById(R.id.tvDifficulty);
        TextView time = (TextView) addView.findViewById(R.id.tvTime);
        TextView serves = (TextView) addView.findViewById(R.id.tvServes);
        ImageView image = (ImageView) addView.findViewById(R.id.ivRecipe);
        recipeName.setText(mCursor.getString(0).toString());
        difficulty.setText(mCursor.getString(5).toString()+" / 5");
        time.setText(mCursor.getString(6).toString()+" min");
        serves.setText(mCursor.getString(7).toString()+"");
        image.setImageResource(getResources().getIdentifier(""+mCursor.getString(8), "drawable", getPackageName()));

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = v.findViewById(R.id.tvRecipeName);
                Intent intent = new Intent(IngredientSearchResult.this, RecipeDisplay.class);
                Bundle activityBundle = new Bundle();//Bundle used to send info across activities
                activityBundle.putString("key_dishname", tv.getText().toString());
                intent.putExtras(activityBundle);
                startActivity(intent);
            }
        });

        layout.addView(addView);

        LinearLayout addView1 = new LinearLayout(IngredientSearchResult.this);
        addView1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 25
        ));
        layout.addView(addView1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(IngredientSearchResult.this, AboutUs.class);
        startActivity(intent);
        return true;
    }

}