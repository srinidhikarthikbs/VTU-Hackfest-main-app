package hackfest.student.vtuhack.activity;

/**
 * Created by Srinidhi on 2015-10-16.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;

import hackfest.student.vtuhack.R;
import hackfest.student.vtuhack.statictry;


public class ProfileFragment extends Fragment {

    String response=null;
    int gotresponse=0;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tv=(TextView) rootView.findViewById(R.id.profiledetails);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                gotresponse=0;
                String url="http://192.168.43.134/vtuhack/getstudentprofile.php?studentid="+ statictry.studentid; //fill this url later and then append the totalamount parameter
                response="some shit"; //may be consider changing it
                HttpClient httpClient=new DefaultHttpClient();
                ResponseHandler responseHandler=new BasicResponseHandler();
                HttpGet httpGet=new HttpGet(url);
                try {
                    response=httpClient.execute(httpGet,responseHandler).toString();
                    gotresponse=1;
                } catch (IOException e) {
                    gotresponse=0;
                    e.printStackTrace();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response=response.trim();
        String[] responsesplit=response.split("-");
        for(int i=0;i<responsesplit.length;i++)
            tv.append(responsesplit[i]+"\n\n");


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}