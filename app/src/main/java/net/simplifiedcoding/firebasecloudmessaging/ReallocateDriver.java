package net.simplifiedcoding.firebasecloudmessaging;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ReallocateDriver extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView txtViewDriver;
    Spinner driver;
    Spinner ress;
    Spinner times;
    TextView reasonof;
    Button Reallocate;
    private ProgressDialog progressDialog;
   // TextView mm;
    TextView oldRess,oldTime;

    //An ArrayList for Spinner Items
    private ArrayList<String> students;
    private ArrayList<String> res;
    private ArrayList<String> time;

    //JSON Array
    private JSONArray result;
    private JSONArray result2;
    private JSONArray result3;
    public String nameOfRes = "";
    public String empnum ="";
  //  Context context;
    ProgressDialog pd1 ;
    AlertDialog alertDialog;
    public String timeSpecificallySelected ="";
    Button setItem = null;
    String [] SmithMUT ={"07:00","08:30","09:00","10:30","13:00","15:00"};
    String [] ShaJehanMUT={"07:00","07:30","08:00","09:00","10:30","13:00","15:00"};
    String [] SeaPointMUT={"07:00","07:30","08:00","10:30","13:00","15:00"};
    String [] PlazaLodgeMUT={"07:00","07:30","08:00","10:30","13:00","15:00"};
    String [] PilglenMUT={"07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] PalmerstonMUT={"07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] UbomboMUT={"07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] FessifernMUT={"07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] ExecHotelMUT={"07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] DurbanHotelMUT={"07:00","08:00"};
    String [] AdrianRoadMUT={"07:00","08:00","10:30","13:00","15:00"};
    String [] BereaCourtMUT={"07:00","08:00","08:30","09:00","10:30","13:00","15:00"};
    String [] AstraMUT={"07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] RenaissanceMUT={"06:30","07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] PoyntonMUT={"06:30","07:00","08:00","08:30","09:00","10:30","13:00","15:00"};
    String [] KillarneyMUT={"06:30","07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] LonsdaleMUT={"06:30","07:00","08:00","09:00","10:30","13:00","15:00"};
    String [] BearnardMUT={"06:30","07:00","08:00","09:00","10:30","13:00","15:00"};

    String residence ="";
    String dilayva = "",residencetime = "";


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reallocate_driver);



        String m = getIntent().getStringExtra("m");

        txtViewDriver = findViewById(R.id.m);
        txtViewDriver.setText(m);
        Reallocate = findViewById(R.id.btnReallocate);

        driver = findViewById(R.id.spnDriver);
        ress = findViewById(R.id.spnRess);
        times = findViewById(R.id.spnTime);

        oldRess = findViewById(R.id.txtvRess);
        oldTime = findViewById(R.id.txtvTime);
        reasonof =  findViewById(R.id.txtReason);
        students = new ArrayList<String>();
        res = new ArrayList<String>();
      //  time = new ArrayList<String>();

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        driver.setOnItemSelectedListener(this);
        ress.setOnItemSelectedListener(this);
        times.setOnItemSelectedListener(this);

        String type = "first";
        BackgroundWorker bkw = new BackgroundWorker(this);
        bkw.execute(type,m);



              /// grab from database from database
            try {

             JSONObject jobj = new JSONObject(bkw.get());

             JSONArray jr =  jobj.getJSONArray("tripDetails");


                for(int i=0;i<jr.length();i++){
                    try {
                        //Getting json object
                        JSONObject json = jr.getJSONObject(i);

                        //Adding the name of the student to array list
                        oldRess.setText(json.getString(Configs.TAG_ResISNames));

                        Toast.makeText(this,json.getString(Configs.TAG_ResISNames),Toast.LENGTH_LONG).show();
                        oldTime.setText(json.getString(Configs.TAG_Time));

                        // Toast.makeText(this,json.getString(Configs.TAG_Time),Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // String result = getIntent().getStringExtra("result");
        //This method will fetch the data from the URL

        getData();
        getData2();
      //  getData0();

      //  getData0();
       // Criteria();

    }

    private void getData(){

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Configs.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array

                            result = j.getJSONArray(Configs.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext().getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void getData2(){

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Configs.DATA_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array


                            result2 = j.getJSONArray(Configs.JSON_ARRAY2);

                            //Calling method getStudents to get the students from the JSON Array
                            getRes(result2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext().getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void getStudents(JSONArray j){

        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(Configs.TAG_EmployeeID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapterComplaint = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, students);
        adapterComplaint.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        driver.setAdapter(adapterComplaint);

        //Setting adapter to show the items in the spinner
        //spinner.setAdapter(new ArrayAdapter<String>( getContext(),android.R.layout.simple_list_item_1, students));
    }

    private void getRes(JSONArray j){
        //Traversing through all the items in the json array

        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                res.add(json.getString(Configs.TAG_ResIS));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapterComplaint = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, res);
        adapterComplaint.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ress.setAdapter(adapterComplaint);

        //Setting adapter to show the items in the spinner
        //spinner.setAdapter(new ArrayAdapter<String>( getContext(),android.R.layout.simple_list_item_1, students));
    }

    ///////for smy spinner ///////////////////

//    private void getData0(){
//
//        //Creating a string request
//        StringRequest stringRequest = new StringRequest(Configs.DATA_URL3,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        JSONObject j = null;
//                        try {
//                            //Parsing the fetched Json String to JSON Object
//                            j = new JSONObject(response);
//
//                            //Storing the Array of JSON String to our JSON Array
//
//
//                            result3 = j.getJSONArray(Configs.JSON_ARRAY4);
//
//                            //Calling method getStudents to get the students from the JSON Array
//                            getOldInfo(result3);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        //Creating a request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext().getApplicationContext());
//
//        //Adding request to the queue
//        requestQueue.add(stringRequest);
//    }
//
//    private void getOldInfo(JSONArray j){
//        //Traversing through all the items in the json array
//
//        for(int i=0;i<j.length();i++){
//            try {
//                //Getting json object
//                JSONObject json = j.getJSONObject(i);
//
//                //Adding the name of the student to array list
//                 oldRess.setText(json.getString(Configs.TAG_ResISNames));
//
//                // Toast.makeText(this,json.getString(Configs.TAG_ResISNames),Toast.LENGTH_LONG).show();
//                 oldTime.setText(json.getString(Configs.TAG_Time));
//
//               // Toast.makeText(this,json.getString(Configs.TAG_Time),Toast.LENGTH_LONG).show();
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//            }
//       }
//
//       // ArrayAdapter<String> adapterComplaint = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, res);
//        //adapterComplaint.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        //ress.setAdapter(adapterComplaint);
//
//        //Setting adapter to show the items in the spinner
//        //spinner.setAdapter(new ArrayAdapter<String>( getContext(),android.R.layout.simple_list_item_1, students));
//    }
////
//    private void getTimeReturn(JSONArray j){
//        //Traversing through all the items in the json array
//
//        for(int i=0;i<j.length();i++){
//            try {
//                //Getting json object
//                JSONObject json = j.getJSONObject(i);
//
//                //Adding the name of the student to array list
//                time.add(json.getString(Configs.JSON_ARRAYTime));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        ArrayAdapter<String> adapterComplaint = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, res);
//        adapterComplaint.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        times.setAdapter(adapterComplaint);
//
//        //Setting adapter to show the items in the spinner
//        //spinner.setAdapter(new ArrayAdapter<String>( getContext(),android.R.layout.simple_list_item_1, students));
//    }





    ////////////////////////////////////////

    //Method to get Employee number of a particular position
    private String getEMployeeNumber(int position){

        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object

            empnum = driver.getSelectedItem().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return empnum;
    }
    //Method to get Employee number of a particular position
    public String getResSelected(int position){

        List<String> resses = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapterList1 = null;
        try {
            //Getting object of given index
            JSONObject json = result2.getJSONObject(position);

            //Fetching name from that object
            nameOfRes = ress.getSelectedItem().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(nameOfRes.equals("385 Smith"))
        {
            resses.addAll(Arrays.asList(SmithMUT));
        }
        if(nameOfRes.equals("Adrian Road"))
        {
            resses.addAll(Arrays.asList(AdrianRoadMUT));
        }


        if(nameOfRes.equals("Bearnard Close"))
        {
            resses.addAll(Arrays.asList(BearnardMUT));


        }
        if(nameOfRes.equals("Astra Hotel"))
        {
            resses.addAll(Arrays.asList(AstraMUT));
        }

        if(nameOfRes.equals("Berea Court"))
        {
            resses.addAll(Arrays.asList(BereaCourtMUT));

        }
        if(nameOfRes.equals("Durban Hotel"))
        {
            resses.addAll(Arrays.asList(DurbanHotelMUT));

        }
        if(nameOfRes.equals("Executive Hotel"))
        {
            resses.addAll(Arrays.asList(ExecHotelMUT));


        }
        if(nameOfRes.equals("Fessifern Building"))
        {
            resses.addAll(Arrays.asList(FessifernMUT));

        }
        if(nameOfRes.equals("Killarney Hotel"))
        {
            resses.addAll(Arrays.asList(KillarneyMUT));

        }
        if(nameOfRes.equals("Lonsdale Hotel"))
        {
            resses.addAll(Arrays.asList(LonsdaleMUT));

        }
        if(nameOfRes.equals("Palmerston Hotel"))
        {
            resses.addAll(Arrays.asList(PalmerstonMUT));

        }
        if(nameOfRes.equals("Pilglen Mews"))
        {
            resses.addAll(Arrays.asList(PilglenMUT));
        }
        if(nameOfRes.equals("Plaza Lodge"))
        {
            resses.addAll(Arrays.asList(PlazaLodgeMUT));
        }
        if(nameOfRes.equals("Poynton House"))
        {
            resses.addAll(Arrays.asList(PoyntonMUT));

        }
        if(nameOfRes.equals("Renaissance Building"))
        {
            resses.addAll(Arrays.asList(RenaissanceMUT));
        }
        if(nameOfRes.equals("Sea Point Towers"))
        {
            resses.addAll(Arrays.asList(SeaPointMUT));

        }
        if(nameOfRes.equals("Sha Jehan Res"))
        {
            resses.addAll(Arrays.asList(ShaJehanMUT));

        }
        if(nameOfRes.equals("Ubombo Res"))
        {
            resses.addAll(Arrays.asList(UbomboMUT));
        }


        arrayAdapterList1= new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,resses);
        times.setAdapter(arrayAdapterList1);
        return nameOfRes;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

      switch (parent.getId()) {
          case R.id.spnRess:
              getResSelected(i);

              break;
          case R.id.spnDriver:
              getEMployeeNumber(i);

              break;
      }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void ReallocateDrivers(View view) {

        String type = "Update";

       residence =  ress.getSelectedItem().toString();
       residencetime = times.getSelectedItem().toString();
        dilayva = driver.getSelectedItem().toString();
        String reallo = reasonof.getText().toString();
        String oldDriver = getIntent().getStringExtra("m");
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,oldDriver,dilayva,residencetime,residence,reallo);

       // sendPush();
        sendMultiplePush();
        /// update spinner
       // getData();
        //getData2();
    }

    private void sendPush() {


       sendMultiplePush();
       //sendSinglePush();


    }

    private void Criteria() {

            String type = "sefing";

            BackgroundWorker backgroundWorker = new BackgroundWorker(this);

            backgroundWorker.execute(type,oldRess.getText().toString(),oldTime.getText().toString());

            Toast.makeText(this,oldRess.getText().toString()+" "+oldTime.getText().toString(),Toast.LENGTH_LONG).show();

    }


    private void sendMultiplePush() {
        final String title = "LEVEL 3 Delay";                  //editTextTitle.getText().toString();
        final String message =reasonof.getText().toString();                // editTextMessage.getText().toString();
        final String image = "";                //editTextImage.getText().toString();


        Criteria();
     //   progressDialog.setMessage("Sending Push");
       // progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_MULTIPLE_PUSH_RES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  progressDialog.dismiss();

                        Toast.makeText(ReallocateDriver.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

//    private void sendSinglePush() {
//        final String title = "";            //editTextTitle.getText().toString();
//        final String message = "";           //editTextMessage.getText().toString();
//        final String image = "";            //editTextImage.getText().toString();
//        final String email = "";            //pinner.getSelectedItem().toString();
//
//        progressDialog.setMessage("Sending Push");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//
//                        Toast.makeText(ReallocateDriver.this, response, Toast.LENGTH_LONG).show();
//                        Log.i("Results",response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("title", title);
//                params.put("message", message);
//
//                if (!TextUtils.isEmpty(image))
//                    params.put("image", image);
//
//                params.put("email", email);
//                return params;
//            }
//        };
//
//        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
//    }
}
