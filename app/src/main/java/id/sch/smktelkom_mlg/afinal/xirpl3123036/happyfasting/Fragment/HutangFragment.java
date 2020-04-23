package id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.Model.Hutang;
import id.sch.smktelkom_mlg.afinal.xirpl3123036.happyfasting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HutangFragment extends Fragment {


    public List<Hutang> hutangList = new ArrayList<>();

    public ArrayAdapter<Hutang> mAdapter;
    ListView listView;

    public HutangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hutang, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHutang);
        listView = view.findViewById(R.id.list_view);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Hutang hutang = hutangList.get(position);
                AlertDialog diabox = AskOption(hutang.getId());
                diabox.show();
                return true;
            }
        });

        listHutang();
        return view;
    }

    private void listHutang() {
        hutangList.clear();
        hutangList = Hutang.listAll(Hutang.class);
        mAdapter = new ArrayAdapter<Hutang>(getActivity(), android.R.layout.simple_list_item_1, hutangList);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private AlertDialog AskOption(final Long position) {

//        final String s = String.valueOf(position)+1;
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        Hutang hutang = Hutang.findById(Hutang.class, position);
                        hutang.delete();
                        listHutang();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

}