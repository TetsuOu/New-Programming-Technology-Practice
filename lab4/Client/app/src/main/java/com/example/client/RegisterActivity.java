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

public class RegisterActivity extends AppCompatActivity {
    private Button submitButton;
    private Button cancleButton;

    private EditText u1;
    private EditText n1;
    private EditText a1;
    private EditText t1;
    private EditText p1;
    private EditText p2;

    private String username;
    private String name;
    private Integer age;
    private String pass1;
    private String pass2;
    private String telenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
    }

    void initview(){
        submitButton = findViewById(R.id.button3);
        cancleButton = findViewById(R.id.button4);
        u1=findViewById(R.id.u1);
        n1=findViewById(R.id.n1);
        a1=findViewById(R.id.a1);
        t1=findViewById(R.id.t1);
        p1=findViewById(R.id.pw1);
        p2=findViewById(R.id.pw2);
        submitButton = findViewById(R.id.button3);
        cancleButton = findViewById(R.id.button4);
    }

    public void RegisterRequest(final String username, final String name, final String password, final Integer age, final String telenum){
        //请求地址
        String url = "http://47.100.39.146:8080/server/registerServlet";
        String tag = "register";
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
                            if (result.equals("TheNameAlreadyExists")) {
                                Toast.makeText(RegisterActivity.this, "The Name Already Exists!", Toast.LENGTH_SHORT).show();
                            } else if(result.equals("TheUsernameAlreadyExists")){
                                Toast.makeText(RegisterActivity.this, "The Username Already Exists!", Toast.LENGTH_SHORT).show();
                            }else {//SignUpSucceed
                                Toast.makeText(RegisterActivity.this, "Sign Up Succeed!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
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
                params.put("name",name);
                params.put("password", password);
                params.put("age", String.valueOf(age));
                params.put("telenum",telenum);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }


    public void Goback(View view){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean check(){

        boolean isok = false;
        username  = u1.getText().toString();
        if(username.isEmpty()){
            toast(" Please enter your username! ");
            return isok;
        }
        else{
            String mess1 = "";
            String pattern1 = "^[a-zA-Z][a-zA-Z\\d_]*";
            String pattern2 = ".*[A-Z].*";
            boolean match1 = Pattern.matches(pattern1, username);
            boolean match2 = Pattern.matches(pattern2, username);
            if (username.length() < 5 || username.length() > 10)
                mess1 += " Username length needs 5-10 digits ";
            if (!match1) {
                mess1 += " Username must start with an English letter and only allow letters, numbers, and _ ";
            }
            if (!match2) {
                mess1 += " Username must contain at least one uppercase letter ";
            }
            if (!mess1.isEmpty()) {
                toast(mess1);
                return isok;
            }

            name   = n1.getText().toString();
            if(name.isEmpty()){
                toast(" Please enter your name! ");
                return isok;
            }
            else{
                String pattern3 = "[a-zA-Z\\d_]*";
                boolean match3 = Pattern.matches(pattern3, name);
                if (!match3) {
                    toast(" Name must only consist of letters, numbers and _ ");
                    return isok;
                }


                if(!a1.getText().toString().isEmpty()){//若填写了年龄
                    age    = Integer.valueOf(a1.getText().toString());
                    String pattern4 = "^([1-9]\\d|\\d)$";
                    boolean match4 = Pattern.matches(pattern4, String.valueOf(age));
                    if (!match4) {
                        toast(" Age can only be an integer of 0-99! ");
                        return isok;
                    }
                }
                else{
                    age = 18;//若未填写年龄，则默认为18岁
//                    toast(" 默认年龄为18岁！ ");
                }

                telenum = t1.getText().toString();
                if(telenum.isEmpty()){
                    toast(" Please enter your phone number! ");
                    return isok;
                }else{
                    String pattern5 = "[1][358]\\d{9}";
                    boolean match5 = Pattern.matches(pattern5, telenum);
                    if (!match5) {
                        toast(" Please enter the correct phone number! ");
                        return isok;
                    }
                }

                pass1  = p1.getText().toString();
                if(pass1.isEmpty()){
                    toast(" Please enter your password! ");
                    return isok;
                }else{
                    String mess = "";
                    if (pass1.length() < 6  || pass1.length() > 12 )
                        mess += " Password length needs 6-12 digits ";
                    String pattern6 = "[a-zA-Z\\d_]*";
                    boolean match6 = Pattern.matches(pattern6, pass1);
                    if (!match6) {
                        mess += " The password should consist of English letters, numbers and _ ";
                    }
                    if (!mess.isEmpty()) {
                        toast(mess);
                        return isok;
                    }

                    pass2  = p2.getText().toString();
                    if(!pass1.equals(pass2)){
                        toast(" Passwords entered twice are inconsistent ");
                        return isok;
                    }
                    else{
                        isok = true;
                        return isok;
                    }
                }

            }
        }
    }




    public void Register(View view)
    {

        if(check()){
            RegisterRequest(username,name,pass1,age,telenum);
        }

    }
}
