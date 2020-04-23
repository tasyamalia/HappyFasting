package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fitur.HikmahActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fitur.LaranganActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fitur.NiatActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fitur.PuasaActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fitur.RukunActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fitur.SyaratActivity;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiturFragment extends Fragment {

    CardView cardViewNiat;
    CardView cardViewPuasa;
    CardView cardViewSyarat;
    CardView cardViewRukun;
    CardView cardViewLarangan;
    CardView cardViewHikmah;

    public FiturFragment
            () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitur, container, false);

        cardViewNiat = view.findViewById(R.id.cardViewNiat);
        cardViewPuasa = view.findViewById(R.id.cardViewPuasa);
        cardViewSyarat = view.findViewById(R.id.cardViewSyarat);
        cardViewRukun = view.findViewById(R.id.cardViewRukun);
        cardViewLarangan = view.findViewById(R.id.cardViewLarangan);
        cardViewHikmah = view.findViewById(R.id.cardViewHikmah);

//        viewNiat();

        cardViewNiat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NiatActivity.class);
                startActivity(intent);
            }
        });

        cardViewPuasa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PuasaActivity.class);
                startActivity(intent);
            }
        });

        cardViewSyarat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SyaratActivity.class);
                startActivity(intent);
            }
        });


        cardViewRukun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RukunActivity.class);
                startActivity(intent);
            }
        });

        cardViewLarangan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LaranganActivity.class);
                startActivity(intent);
            }
        });

        cardViewHikmah.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HikmahActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

//
//    private void viewNiat() {
//        Intent intent = new Intent(getActivity(), NiatActivity.class);
//        startActivity(intent);
//    }

    //public void viewNiat(View view) {
    //    Intent intent = new Intent(getActivity(), NiatActivity.class);
    //       startActivity(intent);
    //}

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.
//        }

//    }


//    public void viewPuasa(View view) {
//        Intent intent = new Intent(FiturFragment.this, PuasaActivity.class);
//        startActivity(intent);
//    }

//    public void viewSyarat(View view) {
//        Intent intent = new Intent(FiturFragment.this, SyaratActivity.class);
//        startActivity(intent);
//    }

//    public void viewRukun(View view) {
//        Intent intent = new Intent(FiturFragment.this, RukunActivity.class);
//        startActivity(intent);
//    }

//    public void viewLarangan (View view) {
//        Intent intent = new Intent(FiturFragment.this, LaranganActivity.class);
//        startActivity(intent);
//    }

//    public void viewHikmah (View view) {
//        Intent intent = new Intent(GridActivity.this, HikmahActivity.class);
//        startActivity(intent);
//    }

}