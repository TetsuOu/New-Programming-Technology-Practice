package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    private Button backToHomeButton;
    private Button viewInfoButton;
    private TextView usname;
    private TextView Welet1;
    private TextView Welet2;
    private TextView Welet3;
    private TextView Welet4;
    private String username;
    private String name;
    private Integer age;
    private String telenum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initview();

        username = getIntent().getStringExtra("username");
        usname.setText(username);
        SignInRequest();
    }

    public void initview() {
        viewInfoButton = findViewById(R.id.button5);
        backToHomeButton = findViewById(R.id.button6);
        Welet1 = findViewById(R.id.welet1);
        Welet2 = findViewById(R.id.welet2);
        Welet3 = findViewById(R.id.welet3);
        Welet4 = findViewById(R.id.welet4);
        usname = findViewById(R.id.tv2);
    }

    public void SignInRequest() {
        //请求地址
        String url = "http://47.100.39.146:8080/server/welcomeServlet";
        String tag = "welcome";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);
        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
//                            String result = jsonObject.getString("Result");

                            name = jsonObject.getString("name");
                            age = Integer.valueOf(jsonObject.getString("age"));
                            telenum = jsonObject.getString("telenum");
//                            System.out.println(username);
//                            System.out.println(name);
//                            System.out.println(age);
//                            System.out.println(telenum);
                        } catch (JSONException e) {
                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public void BackToHome (View view){
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public  void  ViewInfo(View view){

        Welet1.setText("Username: "+username);
        Welet2.setText("Name: "+name);
        Welet3.setText("Age: "+String.valueOf(age));
        Welet4.setText("Telenum: "+telenum);
    }
}