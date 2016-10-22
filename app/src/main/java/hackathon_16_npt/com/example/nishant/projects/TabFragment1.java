package hackathon_16_npt.com.example.nishant.projects;

/**
 * Created by DELL on 10/21/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TabFragment1 extends Fragment {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private int contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return inflater.inflate(R.layout.tab_fragment_1, container, false);


    }


}