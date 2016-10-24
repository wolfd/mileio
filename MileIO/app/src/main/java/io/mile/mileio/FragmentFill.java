package io.mile.mileio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by DHZ_Bill on 10/20/16.
 */
public class FragmentFill extends Fragment{
    @BindView(R.id.fillListView) ListView fillListView;
    @BindView(R.id.gasCost) TextView gasCost;
    @BindView(R.id.amountFilled) TextView amountFilled;
    public FragmentFill(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fill_screen, container, false);
        ButterKnife.bind(this,v);
        ArrayList<FillOweAmount> userList = new ArrayList<>();
        final AdapterFillScreen adapter = new AdapterFillScreen(this.getContext(), userList);
        fillListView.setAdapter(adapter);
        FillOweAmount a = new FillOweAmount("Bill", "200");
        adapter.add(a);
        return v;
    }
}
