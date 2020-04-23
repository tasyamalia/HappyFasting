package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Model.AladhanResponse;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Model.Hutang;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.R;

public class TanggalAdapter extends RecyclerView.Adapter<TanggalAdapter.ViewHolder> {
    ArrayList<AladhanResponse> list;
    String day;
    Integer arafah = 10;

    Context context;


    public TanggalAdapter(ArrayList<AladhanResponse> mList, Context mContext) {
        this.list = mList;
        this.context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AladhanResponse data = list.get(position);


        if (data != null) {
            Log.i("list", String.valueOf(data.day_gregorian));
            holder.tvJudul.setText(data.day_gregorian);
            holder.tvDeskripsi.setText(data.day_hijri);
           
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat sdfDay = new SimpleDateFormat("d", Locale.US);
            SimpleDateFormat sdfMonth = new SimpleDateFormat("M", Locale.US);

            if (data.day_gregorian.equals(sdfDay.format(c1.getTime()))
                    && data.month_number_gre.equals(sdfMonth.format(c1.getTime()))) {
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            // Yaumul bidh
            if (data.day_hijri.equals("01")) {
                Log.i("day/hijri", data.day_hijri);
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF9900"));
            }
            if (data.day_hijri.equals("02")) {
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF9900"));
            }
            if (data.day_hijri.equals("03")) {
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF9900"));
            }

            if (data.day_name.equals("Monday")) {
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF00FF"));
            }
            if (data.day_name.equals("Thursday")) {
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF00FF"));
            }


            //Arafah
            if (data.month_number_hij.equals("12")) {
                if (data.day_hijri.equals("09")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFE600"));
                }
                //Haram Puasa
                if (data.day_hijri.equals("11")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF0000"));

                }

                if (data.day_hijri.equals("12")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF0000"));

                }

                if (data.day_hijri.equals("13")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF0000"));

                }
            }

            //Asyura dan tasyua
            if (data.month_number_hij.equals("1")) {
                if (data.day_hijri.equals("09")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#602F75"));
                }
                if (data.day_hijri.equals("10")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#602F75"));
                }
            }
            // Ramadhan
            if (data.month_number_hij.equals("9")) {
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#0000FF"));

                holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder myQuittingDialogBox = new AlertDialog.Builder(context)
                                //set message, title, and icon
                                .setTitle("Hutang")
                                .setMessage("Apakah hari ini tidak puasa ?")
                                .setIcon(R.drawable.ic_event_busy_black_24dp)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //your deleting code
                                        Hutang hutang = new Hutang(data.day_gregorian, data.day_hijri, data.date_gregorian, data.date_hijri,
                                                data.month_name_gre, data.month_number_gre, data.month_name_hij, data.month_number_hij,
                                                data.day_name);

                                        hutang.save();


                                        Log.i("hutang", String.valueOf(hutang));
                                        holder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                                    }

                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();

                                    }
                                });
                        final AlertDialog alert = myQuittingDialogBox.create();
                        alert.show();
                    }


                });
            }

            //Haram Puasa
            if (data.month_number_hij.equals("10")) {
                if (data.day_hijri.equals("01")) {
                    holder.relativeLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }


        } else if (data == null) {

            holder.tvJudul.setText(" ");
            holder.tvDeskripsi.setText(" ");
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#E0FFFF"));
        }

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul;
        TextView tvDeskripsi;
        FrameLayout frameLayout;
        //        RelativeLayout relativeLayout;
        View relativeLayout;

        public ViewHolder(View v) {
            super(v);

            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvDeskripsi = itemView.findViewById(R.id.textViewDeskripsi);
            relativeLayout = itemView.findViewById(R.id.viewColor);
//            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeL);
            frameLayout = itemView.findViewById(R.id.relativeL);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}