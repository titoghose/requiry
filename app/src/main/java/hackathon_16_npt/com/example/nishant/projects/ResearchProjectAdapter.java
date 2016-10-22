package hackathon_16_npt.com.example.nishant.projects;

/**
 * Created by Work on 03/10/16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResearchProjectAdapter extends ArrayAdapter<ResearchProject>{






    Context mContext; //Adapter context
    int mLayoutResourceId; //Adapter View Layout


    /*  ResearchProjectAdapter that calls constructor of ArrayAdapter with mContext and
        mLayoutResourceId as parameters */

    public ResearchProjectAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }


    // Returns view of one item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final ResearchProject currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);

        final TextView tv1 = (TextView) row.findViewById(R.id.mainProjectNameDisplay);
        tv1.setText(currentItem.getProjectName());

        final TextView tv2 = (TextView) row.findViewById(R.id.mainProjectCreatorDisplay);
        tv2.setText("by "+currentItem.getAuthorName());


        return row;
    }
}
