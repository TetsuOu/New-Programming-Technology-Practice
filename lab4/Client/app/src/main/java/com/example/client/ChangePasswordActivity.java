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
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {
    private String username;
    private String pass1;
    private String pass2;

    private Button btn_submit;
    private Button btn_home;


    private EditText p1;
    private EditText p2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        username = getIntent().getStringExtra("username");
        initview();
    }

    public void initview(){
        btn_submit = findViewById(R.id.button9);
        btn_home = findViewById(R.id.button10);
        p1=findViewById(R.id.npw);
        p2=findViewById(R.id.npw2);

    }



    public void ChangePasswordRequest(final String username, final String password) {
        //请求地址
        String url = "http://47.100.39.146:8080/server/changePasswordServlet";
        String tag = "ChangePassword";
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
                            String result = jsonObject.getString("Result");
                            if (result.equals("ChangeSucceed")) {
                                Toast.makeText(ChangePasswordActivity.this, "Change Succeed!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else if (result.equals("ChangeFail")) {
                                Toast.makeText(ChangePasswordActivity.this, "The username does not exist!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "纳尼", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                            Log.e("TAG", e.getMessage(), e);
                            toast("无网络连接");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                toast("请稍后重试");
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };


        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);

    }

    public void BackToHome (View view){
        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangePasswordActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean check() {

        pass1 = p1.getText().toString();
        pass2 = p2.getText().toString();
        boolean isok = false;
        boolean f1 = true;
        //检查密码

        String mess = "";
        if (pass1.isEmpty()) {
            toast(" Please enter your new password! ");
            f1 = false;
        } else {
            if (pass1.length() < 6 || pass1.length() > 12)

                mess += " Password length needs 6-12 digits ";

            String pattern6 = "[a-zA-Z\\d_]*";

            boolean match6 = Pattern.matches(pattern6, pass1);

            if (!match6) {

                mess += " The password should consist of English letters, numbers and _ ";

            }


            if (!mess.isEmpty()) {
                toast(mess);
                f1 = false;
            }
        }

        if (f1) {
            if (!pass1.equals(pass2)) {
                toast(" Passwords entered twice are inconsistent ");
            }else{
                isok = true;
            }
        }

        return isok;
    }

    public void change (View view){
        if(check()){

            ChangePasswordRequest(username,pass1);
        }

    }

}
