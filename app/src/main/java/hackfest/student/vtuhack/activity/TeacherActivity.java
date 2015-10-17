package hackfest.student.vtuhack.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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

/**
 * Created by Srinidhi on 2015-10-17.
 */
public class TeacherActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    String response=null;
    int gotresponse=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int count=0;
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        //BarChart chart = (BarChart) findViewById(R.id.bc1);
        BarChart chart=new BarChart(this);
        setContentView(chart);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                gotresponse=0;
                String url="http://192.168.43.134/vtuhack/subjectwiseteacher.php?teacherid="+ statictry.teacherid; //fill this url later and then append the totalamount parameter
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

        chart.setDescription("States statistics");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.animateY(4000);
    }


}
