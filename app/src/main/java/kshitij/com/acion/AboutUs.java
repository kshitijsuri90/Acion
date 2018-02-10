package kshitij.com.acion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Punit on 08-02-2018.
 */

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        getSupportActionBar().hide();
    }
}
