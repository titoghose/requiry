package hackathon_16_npt.com.example.nishant.projects;

/**
 * Created by DELL on 10/21/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TabFragment1 extends Fragment {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private int contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE);
        Profile p = new Profile();
        p.setName(sharedPreferences.getString("nameKey",""));
        p.setUsername(sharedPreferences.getString("usernameKey",""));
        p.setPassword(sharedPreferences.getString("passwordKey",""));
        p.setEmail(sharedPreferences.getString("emailKey",""));
        p.setPhone_no(sharedPreferences.getString("phoneKey",""));
        p.setDesignation(sharedPreferences.getString("designationKey",""));
        p.setQualification(sharedPreferences.getString("qualificationsKey",""));
        p.setInterest1(sharedPreferences.getString("interest1Key",""));
        p.setInterest2(sharedPreferences.getString("interest2Key",""));
        p.setInterest3(sharedPreferences.getString("interest3Key",""));
        p.setProfilePicURL(sharedPreferences.getString("profilePicURLKey",""));
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        ImageView profile = (ImageView) v.findViewById (R.id.profilePic);
        new ImageLoadTask(p.getProfilePicURL(), profile).execute();

        TextView profile_name = (TextView) v.findViewById(R.id.profile_name);
        profile_name.setText(p.getName());

        TextView profile_designation = (TextView) v.findViewById(R.id.profile_designation);
        profile_designation.setText(p.getDesignation());

        TextView profile_email = (TextView) v.findViewById(R.id.profile_emailinput);
        profile_email.setText(p.getEmail());

        TextView profile_phone = (TextView) v.findViewById(R.id.profile_contactinput);
        profile_phone.setText(p.getPhone_no());

        TextView profile_qualification = (TextView) v.findViewById(R.id.profile_qualification);
        profile_qualification.setText(p.getQualification());

        InterestAdapter mAdapter = new InterestAdapter(this.getActivity(), R.layout.interest_text_view);
        mAdapter.add(p.getInterest1());
        mAdapter.add(p.getInterest2());
        mAdapter.add(p.getInterest3());
        ListView listViewToDo = (ListView) v.findViewById(R.id.interest_listview);
        listViewToDo.setAdapter(mAdapter);
        return v;
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }


}