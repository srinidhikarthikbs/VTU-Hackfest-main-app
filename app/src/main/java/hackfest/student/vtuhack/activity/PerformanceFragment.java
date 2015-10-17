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


public class PerformanceFragment extends Fragment {

    String response=null;
    int gotresponse=0;

    public PerformanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_performance, container, false);

        int count=0;
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        //BarChart chart = (BarChart) findViewById(R.id.bc1);
        BarChart chart=new BarChart(getActivity());
        //setContentView(chart);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                gotresponse=0;
                String url="http://192.168.43.134/vtuhack/subjectwisestudent.php?studentid="+ statictry.studentid; //fill this url later and then append the totalamount parameter
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



        for (String retval: response.split(",")){

            int i=0;
            String name=null,value=null;
            for (String retval2: retval.trim().split("-")){

                if(i==0) {
                    labels.add(retval2);
                    i++;
                }
                else {
                    entries.add(new BarEntry(Float.parseFloat(retval2), count++));
                }
            }
        }

        BarDataSet dataset = new BarDataSet(entries, "Average Percentages");
        BarData data = new BarData(labels, dataset);
        chart.setData(data);

        chart.setDescription("Student statistics");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.animateY(4000);


        // Inflate the layout for this fragment
        return chart;
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