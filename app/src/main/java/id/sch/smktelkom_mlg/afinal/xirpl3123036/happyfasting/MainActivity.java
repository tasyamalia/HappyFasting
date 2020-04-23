package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Adapter.TanggalAdapter;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Model.AladhanResponse;

public class MainActivity extends AppCompatActivity {

    ArrayList<AladhanResponse> mList = new ArrayList<>();
    TanggalAdapter mAdapter;
    TextView textview;
    TextView textViewBulan;
    String day, month, year;
    Integer next;
    Button button;
    Calendar c1, nextMonth;
    ImageView right, left;
    SimpleDateFormat sdfYear, sdfMonth, sdfDay, nextMonth1;

    ProgressDialog dialog;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private void dateMinPlus(Integer numDay) {

        c1.add(Calendar.MONTH, numDay);
        year = sdfYear.format(c1.getTime());
        month = sdfMonth.format(c1.getTime());
        day = sdfDay.format(c1.getTime());
        textViewBulan.setText("Bulan ke - " + month);
        donwloadDataSources(year, month, day);

    }

    private void monthMin() {
        mList.clear();
        c1.add(Calendar.MONTH, -1);
        int month = c1.get(Calendar.MONTH) + 1;
        Log.i("month/year", String.valueOf(month));
        textViewBulan.setText("Bulan ke -" + month);
        donwloadDataSources(year, String.valueOf(month), day);

    }

    private void monthPlus() {
        mList.clear();
        c1.add(Calendar.MONTH, 1);
        int month = c1.get(Calendar.MONTH) + 1;
        Log.i("month/year", String.valueOf(month));
        textViewBulan.setText("Bulan ke - " + month);
        donwloadDataSources(year, String.valueOf(month), day);
    }

    private void donwloadDataSources(String year, String month, String day) {
        dialog = ProgressDialog.show(this, "", "Loading...");
        String url = "http://api.aladhan.com/gToHCalendar/" + month + "/" + year;
        Log.i("ReqURL", url);

        RequestQueue queue = Volley.newRequestQueue(this);

        // Instantiate the RequestQueue.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response success", response);
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("response error", error.toString());
                        AlertDialog.Builder Adialog = new AlertDialog.Builder(MainActivity.this).setCancelable(false)
                                .setTitle("Koneksi Gagal").setMessage("Gagal tersambung dengan server.")
                                .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        final AlertDialog alert = Adialog.create();
                        alert.show();
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void parseResponse(String response) {


        mList.clear();
        try {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("data");
            Log.i("Panjang", String.valueOf(array.length()));

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                //
                JSONObject objHij = obj.getJSONObject("hijri");
                JSONObject objGre = obj.getJSONObject("gregorian");
                //
                JSONObject monthGre = objGre.getJSONObject("month");
                JSONObject montHij = objHij.getJSONObject("month");
                //
                JSONObject weekday = objGre.getJSONObject("weekday");

                if (i == 0) {
                    int hariKe = 0;
                    if (weekday.getString("en").equals("Tuesday"))
                        hariKe = 1;
                    else if (weekday.getString("en").equals("Wednesday"))
                        hariKe = 2;
                    else if (weekday.getString("en").equals("Thursday"))
                        hariKe = 3;
                    else if (weekday.getString("en").equals("Friday"))
                        hariKe = 4;
                    else if (weekday.getString("en").equals("Saturday"))
                        hariKe = 5;
                    else if (weekday.getString("en").equals("Sunday"))
                        hariKe = 6;


                    for (int j = 0; j < hariKe; j++) {
                        mList.add(null);
                    }

                    Log.i("hari", String.valueOf(hariKe));
                }

                mList.add(new AladhanResponse(objGre.getString("day"), objHij.getString("day"),
                        objGre.getString("date"), objHij.getString("date"), monthGre.getString("en"),
                        monthGre.getString("number"), montHij.getString("en"), montHij.getString("number"),
                        weekday.getString("en")));
            }
//            Log.i("mlist", String.valueOf(mList.get(0)));
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 750);
    }

//    private void fillData() {
//        Resources resources = getResources();
//        String[] arJudul = resources.getStringArray(R.array.places);
//        String[] arDeskripsi = resources.getStringArray(R.array.place_desc);
//        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
//        Drawable[] arFoto = new Drawable[a.length()];
//
//        for (int i = 0; i < arFoto.length; i++) {
//            BitmapDrawable bd = (BitmapDrawable) a.getDrawable(i);
//            RoundedBitmapDrawable rbd =
//                    RoundedBitmapDrawableFactory.create(getResources(),bd.getBitmap());
//            rbd.setCircular(true);
//            arFoto[i] = rbd;
//        }
//        a.recycle();
//
//        for (int i = 0; i < arJudul.length; i++) {
//            mList.add(new Hotel(arJudul[i],arDeskripsi[i],arFoto[i]));
//        }
//        mAdapter.notifyDataSetChanged();
//    }

    public void ButtonOnClickHome(View V) {
        textview.setText("Menampilkan Home");
        textview.setVisibility(View.VISIBLE);
    }

    public void ButtonOnClickSearch(View V) {
        textview.setText("Menampilkan Search");
        textview.setVisibility(View.VISIBLE);
    }

    public void ButtonOnClickAdd(View V) {
        textview.setText("Menampilkan Add");
        textview.setVisibility(View.VISIBLE);
    }

    public void ButtonOnClickFavorite(View V) {
        textview.setText("Menampilkan Favorite");
        textview.setVisibility(View.VISIBLE);
    }

    public void ButtonOnClickProfile(View V) {
        textview.setText("Menampilkan Profile");
        textview.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        textview = findViewById(R.id.textView);
        textViewBulan = findViewById(R.id.textViewBulan);
        right = findViewById(R.id.rightView);

        left = findViewById(R.id.leftView);

        c1 = Calendar.getInstance();


        sdfYear = new SimpleDateFormat("y", Locale.US);
        sdfMonth = new SimpleDateFormat("M", Locale.US);
        sdfDay = new SimpleDateFormat("d", Locale.US);

        year = sdfYear.format(c1.getTime());
        month = sdfMonth.format(c1.getTime());
        day = sdfDay.format(c1.getTime());

        textViewBulan.setText("Bulan ke - "+month);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                monthPlus();
                dateMinPlus(1);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                monthMin();
                dateMinPlus(-1);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 7);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TanggalAdapter(mList, this);
        recyclerView.setAdapter(mAdapter);

//        fillData();
//
//        Log.i("month/year", String.valueOf(month)+String.valueOf(year));
        donwloadDataSources(year, month, day);
    }
}