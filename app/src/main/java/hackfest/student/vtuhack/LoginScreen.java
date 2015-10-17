package hackfest.student.vtuhack;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import hackfest.student.vtuhack.activity.FirstActivity;
import hackfest.student.vtuhack.activity.MainActivity;
import hackfest.student.vtuhack.activity.TeacherActivity;


public class LoginScreen extends Activity {

    TextView email, password;
    String username, pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login = (Button) findViewById(R.id.login);

        email = (TextView) findViewById(R.id.tv1);email.setText("itsvatty@gmail.com");
        password = (TextView) findViewById(R.id.tv2);password.setText("1234tintin");

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email.setBackgroundResource(R.drawable.edit_text_design_focus);
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password.setBackgroundResource(R.drawable.edit_text_design_focus);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = email.getText().toString();
                pass = password.getText().toString();

                loginphp k = new loginphp();
                k.execute();
            }
        });
    }

    class loginphp extends AsyncTask<Void, Void, String> {
        private loginphp() {
        }

        @Override
        protected String doInBackground(Void... params) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.134/vtuhack/edulogin.php?username="+username+"&password="+pass);
            String resp = new String();

            try {
                // Execute HTTP Post Request

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);

                //This is the response from a php application

                System.out.println(response);
                //Toast.makeText(this, "response : " + response, Toast.LENGTH_LONG).show();      """"USED FOR DEBUGGING ONLY""""""
                resp = response;

            } catch (ClientProtocolException e) {
                //Toast.makeText(this, "CPE response " + e.toString(), Toast.LENGTH_LONG).show();
                // TODO Auto-generated catch block
            } catch (IOException e) {
                //Toast.makeText(this, "IOE response: Internet Connection Not Available"/* + e.toString()*/, Toast.LENGTH_LONG).show();
                // TODO Auto-generated catch block
            }

            //flag = 0;
            return resp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            String[] avoidsplit=aVoid.split("-");
            Intent i;
            switch(avoidsplit[0]) {
                case "teacher":
                    i=new Intent(getApplicationContext(), TeacherActivity.class);
                    statictry.teacherid=avoidsplit[1];
                    //i.putExtra("teacherid",avoidsplit[1]);
                    startActivity(i);
                    System.out.println("teacher");
                    break;
                case "student":
                    i=new Intent(getApplicationContext(), FirstActivity.class);
                    statictry.studentid=avoidsplit[1];
                    //i.putExtra("studentid", avoidsplit[1]);
                    startActivity(i);
                    System.out.println("student");

                    break;
                case "government":
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    System.out.println("government");
                    break;
                case "company":
                    break;
                case "school":
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Invalid Email ID or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
