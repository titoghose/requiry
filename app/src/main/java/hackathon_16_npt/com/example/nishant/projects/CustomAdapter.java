package hackathon_16_npt.com.example.nishant.projects;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by DELL on 10/5/2016.
 */
 class CustomAdapter extends ArrayAdapter<String> {


    public CustomAdapter(Context context, String []foods) {
        super(context,R.layout.custom_row, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater infalter = LayoutInflater.from(getContext());
        View customView = infalter.inflate(R.layout.custom_row, parent,false);

        String singleFoodItem = getItem(position);
        TextView projText = (TextView) customView.findViewById(R.id.custom_text);
        ImageView projImage = (ImageView) customView.findViewById(R.id.projImage);

        projText.setText(singleFoodItem);
        projImage.setImageResource(R.drawable.top_hat);

        return customView;
    }
}
