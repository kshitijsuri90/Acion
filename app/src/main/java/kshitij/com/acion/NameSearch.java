package kshitij.com.acion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NameSearch extends AppCompatActivity {
    private EditText dish;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Recipe by Name");
        setContentView(R.layout.name_search);

        dish = findViewById(R.id.dishText);
        search = findViewById(R.id.findButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dishname = dish.getText().toString();
                    if (dishname.length() == 0) {
                        Toast.makeText(NameSearch.this, "No search value was entered.", Toast.LENGTH_LONG).show();
                        return ;
                    }
                    Intent intent = new Intent(NameSearch.this, NameSearchResult.class);
                    Bundle activityBundle = new Bundle();//Bundle used to send info across activities
                    activityBundle.putString("key_dishname", dishname);
                    intent.putExtras(activityBundle);
                    startActivity(intent);
                }
                catch (RuntimeException e)
                {
                    Toast.makeText(NameSearch.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(NameSearch.this, AboutUs.class);
        startActivity(intent);
        return true;
    }
}

