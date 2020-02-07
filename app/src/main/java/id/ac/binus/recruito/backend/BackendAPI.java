package id.ac.binus.recruito.backend;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import id.ac.binus.recruito.models.User;

public class BackendAPI {

    private static final String TAG = "BackendAPI";
    private static final String BASE_URL = "http://recruito-binus.000webhostapp.com/api/";

    Context context;
    static User user;
    static boolean flag;

    public BackendAPI(Context context) {
        this.context = context;
    }

    public User logIn(String email, final String password){

        JSONObject param = new JSONObject();
        try{
            param.put("username", email);
            param.put("password", password);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "login",
                param,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            JSONArray data = response.getJSONArray("data");
                            JSONObject object = data.getJSONObject(0);

                            Log.d(TAG, "onResponse: Object = " + object);

                            int userID = Integer.parseInt(object.getString("UserID"));
                            int imageID = Integer.parseInt(object.getString("ImageID"));
                            String username = object.getString("UserName");
                            String DOB = object.getString("DOB");
                            String phoneNumber = object.getString("PhoneNumber");
                            String userStatus = object.getString("UserStatus");
                            String email = object.getString("Email");
                            String UserPassword = object.getString("UserPassword");

                            // Calculate Age
                            Calendar today = Calendar.getInstance();
                            int age = today.get(Calendar.YEAR) - Integer.parseInt(DOB.substring(0, 4));

                            user = new User(userID, imageID, username, DOB, age, "male", phoneNumber, userStatus, email, UserPassword, "turtle");


                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: JSONException = " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return user;
    }

    public boolean Register(String username, String password, String email, String dob, String status, String phone) {

        JSONObject param = new JSONObject();
        try {
            param.put("username", username);
            param.put("password", password);
            param.put("email", email);
            param.put("dob", dob);
            param.put("status", status);
            param.put("phone", phone);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "register",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        flag = false;
                        try {
                            if (response.getBoolean("status") == true) {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                flag = true;
                            } else {
                                Toast.makeText(context, "Failed when registering, please try again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }
        );


        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return flag;

    }

    public boolean AddThread(String Creator, String Title, String Time, String Date, String Address, String Desc, int TotalPeople, int CategoryID) {

        JSONObject param = new JSONObject();
        try {
            param.put("Creator", Creator);
            param.put("Title", Title);
            param.put("Time", Time);
            param.put("Date", Date);
            param.put("Address", Address);
            param.put("Desc", Desc);
            param.put("TotalPeople", TotalPeople);
            param.put("CategoryID", CategoryID);
        } catch (Exception e) {

        }

        JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.POST,
                BASE_URL + "thread",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        flag = false;
                        try {
                            if (response.getBoolean("status") == true) {
                                try {
                                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    flag = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        flag = false;
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }

        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return flag;
    }

}
