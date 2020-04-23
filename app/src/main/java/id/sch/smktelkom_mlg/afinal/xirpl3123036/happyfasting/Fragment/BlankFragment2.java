package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.ColorActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Model.AladhanResponse;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {


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
    Button buttonColor;

    ProgressDialog dialog;

    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);

        textview = view.findViewById(R.id.textView);
        textViewBulan = view.findViewById(R.id.textViewBulan);
        right = view.findViewById(R.id.rightView);

        left = view.findViewById(R.id.leftView);

        buttonColor = view.findViewById(R.id.buttonColor);



        c1 = Calendar.getInstance();


        sdfYear = new SimpleDateFormat("y", Locale.US);
        sdfMonth = new SimpleDateFormat("M", Locale.US);
        sdfDay = new SimpleDateFormat("d", Locale.US);

        year = sdfYear.format(c1.getTime());
        month = sdfMonth.format(c1.getTime());
        day = sdfDay.format(c1.getTime());

        textViewBulan.setText("Bulan "+convertMonth(Integer.parseInt(month)));

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


        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TanggalAdapter(mList, getActivity());
        recyclerView.setAdapter(mAdapter);

        donwloadDataSources(year, month, day);


        //TODO create a visit from the data from webcall (in appObj) for info passed to email, text etc



        buttonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ColorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void donwloadDataSources(String year, String month, String day) {
        dialog = ProgressDialog.show(getActivity(), "", "Loading...");
        String url = "http://api.aladhan.com/gToHCalendar/"+month+"/"+year;
        Log.i("ReqURL", url);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

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
                        AlertDialog.Builder Adialog = new AlertDialog.Builder(getActivity()).setCancelable(false)
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
                JSONObject objHij = obj.getJSONObject("hijri");
                JSONObject objGre = obj.getJSONObject("gregorian");
                JSONObject monthGre = objGre.getJSONObject("month");
                JSONObject montHij = objHij.getJSONObject("month");
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

    private void dateMinPlus(Integer numDay) {

        c1.add(Calendar.MONTH, numDay);
        year = sdfYear.format(c1.getTime());
        month = sdfMonth.format(c1.getTime());
        day = sdfDay.format(c1.getTime());
        textViewBulan.setText("Bulan "+convertMonth(Integer.parseInt(month)));
        donwloadDataSources(year, month, day);

    }

    public String convertMonth (int months){
        String month = String.valueOf(months);
        if(month.equals("1")){
            return "Januari";
        }else if(month.equals("2")){
            return "Februari";
        }else if(month.equals("3")){
            return "Maret";
        }else if(month.equals("4")){
            return "April";
        }else if(month.equals("5")){
            return "Mei";
        }else if(month.equals("6")){
            return "Juni";
        }else if(month.equals("7")){
            return "Juli";
        }else if(month.equals("8")){
            return "Agustus";
        }else if(month.equals("9")){
            return "September";
        }else if(month.equals("10")){
            return "Oktober";
        }else if(month.equals("11")){
            return "November";
        }else{
            return "Desember";
        }
    }
}