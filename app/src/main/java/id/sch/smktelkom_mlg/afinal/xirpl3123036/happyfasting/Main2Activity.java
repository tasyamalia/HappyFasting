package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fragment.BlankFragment2;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fragment.FiturFragment;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fragment.HutangFragment;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;
    ImageButton imageButtonAbout;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.openBrowser:
                Intent about = new Intent(Main2Activity.this, AboutActivity.class);

                startActivity(about);

                return true;

        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.containerTry, new BlankFragment2())
//                            .commit();
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
            changePage(item.getItemId());
            return false;
        }
    };

    private void changePage(int itemId) {
        switch (itemId) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerTry, new BlankFragment2())
                        .commit();
                setTitle(R.string.title_home);
                break;
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerTry, new HutangFragment())
                        .commit();
                setTitle(R.string.title_dashboard);
                break;
            case R.id.navigation_notifications:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerTry, new FiturFragment())
                        .commit();
                setTitle("Fitur");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        changePage(R.id.navigation_home);
//        imageButtonAbout = findViewById(R.id.imageButtonAbout);

    }



}