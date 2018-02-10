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

public class NameSearchResult extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Results");
        setContentView(R.layout.list_result);
        layout = (LinearLayout) findViewById(R.id.grid_view);

        //get search value
        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("key_dishname");
        }

        try {
            Context mContext = getApplicationContext();
            final SQLiteDatabase mDatabase = new DBHelper(mContext).getReadableDatabase();

            value = "SELECT * FROM appdb1 WHERE name LIKE \'%"+value+"%\';";
            Cursor mCursor = mDatabase.rawQuery(value, null);
            int count = mCursor.getCount();

            if (count == 0) {
                emptyResult();
            }
            else {
                mCursor.moveToFirst();
                for (int i = 0; i < count; i++) {
                    addRow(mCursor);
                    mCursor.moveToNext();
                }
            }
        }   catch (RuntimeException e) {
            Toast.makeText(NameSearchResult.this, e+"", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NameSearchResult.this, NameSearch.class);
            startActivity(intent);
        }
    }

    public void emptyResult() {
        LinearLayout addView = new LinearLayout(NameSearchResult.this);
        addView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.addView(addView);
        TextView textView = new TextView(this);
        textView.setText("No matches were found");
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(textView);
    }

    public void addRow(Cursor mCursor) {

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
                Intent intent = new Intent(NameSearchResult.this, RecipeDisplay.class);
                Bundle activityBundle = new Bundle();//Bundle used to send info across activities
                activityBundle.putString("key_dishname", tv.getText().toString());
                intent.putExtras(activityBundle);
                startActivity(intent);
            }
        });

        layout.addView(addView);

        LinearLayout addView1 = new LinearLayout(NameSearchResult.this);
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
        Intent intent = new Intent(NameSearchResult.this, AboutUs.class);
        startActivity(intent);
        return true;
    }
}
