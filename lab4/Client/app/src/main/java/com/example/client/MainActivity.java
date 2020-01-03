package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ToggleButton tbPasswordVisibility;

    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        //2、获取组件
        this.passwordEditText = (EditText) findViewById(R.id.et2);
        this.tbPasswordVisibility = (ToggleButton) findViewById(R.id.tb_password_visibility);
        //4、事件注册
        this.tbPasswordVisibility.setOnCheckedChangeListener(new ToggleButtonClick());

    }

    public void initview(){
        signInButton = findViewById(R.id.button1);
        usernameEditText = findViewById(R.id.et1);
        passwordEditText = findViewById(R.id.et2);
    }

    public void SignInRequest(final String username, final String password){
        //请求地址
        String url = "http://47.100.39.146:8080/server/loginServlet";
        String tag = "Login";
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
                            if (result.equals("TheUserDoesNotExist")) {
                                Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                            } else if(result.equals("PasswordError")){
                                Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Correct password", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                                intent.putExtra("username",username);
                                startActivity(intent);
                            }
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
                params.put("password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public void ForgetRequest(final String username, final String password){
        //请求地址
        String url = "http://47.100.39.146:8080/server/loginServlet";
        String tag = "Login";
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
                            if (result.equals("TheUserDoesNotExist")) {
                                Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Username exists, jump to forgot password interface", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                                intent.putExtra("username",username);
                                startActivity(intent);
                            }
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
                params.put("password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public void Login(View view){
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(MainActivity.this,"Please Input Username!",Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){
            Toast.makeText(MainActivity.this,"Please Input Password!",Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(MainActivity.this,username+" "+password,Toast.LENGTH_SHORT).show();
        else {
            SignInRequest(username,password);

        }

    }

    public void SignUp(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void Forget(View view){
        username = usernameEditText.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(MainActivity.this,"Please Input Username!",Toast.LENGTH_SHORT).show();
        }else{
            ForgetRequest(username,"!!@@##$$");
        }
    }


    //3、密码可见性按钮监听
    private class ToggleButtonClick implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            //5、判断事件源的选中状态
            if (isChecked){

                //显示密码
                //etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                // 隐藏密码
                //etPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
            //6、每次显示或者关闭时，密码显示编辑的线不统一在最后，下面是为了统一
            passwordEditText.setSelection(passwordEditText.length());
        }
    }



}
