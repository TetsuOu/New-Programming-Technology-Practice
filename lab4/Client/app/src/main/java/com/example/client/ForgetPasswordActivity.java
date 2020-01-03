package com.example.client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_submit;
    private Button btn_backtohome;
    private Button btn_check;
    private EditText et_phonenum;
    private EditText et_verifycode;

    private TimerTask tt;
    private Timer tm;
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s,我们前端会有差异的建议设置90，100或者120
    public String country="86";//这是中国区号，如果需要其他国家列表，可以使用getSupportedCountries();获得国家区号

    private static final int CODE_REPEAT = 1; //重新发送

    private String username;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initview();
        username = getIntent().getStringExtra("username");
        MobSDK.init(this,"2d7aeeb744c34","52bc4839ce97b896ffe7b32bc11419ac");//注册自己的
        SMSSDK.registerEventHandler(eh); //注册短信回调（记得销毁，避免泄露内存）
    }

    public void initview(){
        btn_submit = findViewById(R.id.button7);
        btn_backtohome = findViewById(R.id.button8);
        btn_check = findViewById(R.id.check);
        et_phonenum = findViewById(R.id.phonenum);
        et_verifycode = findViewById(R.id.verifycode);
        btn_check.setOnClickListener((View.OnClickListener) this);
        btn_submit.setOnClickListener((View.OnClickListener) this);

    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetPasswordActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ForgetPasswordRequest(final String username, final String telenum){
        //请求地址
        String url = "http://47.100.39.146:8080/server/forgetPasswordServlet";
        String tag = "forgetPassword";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);
        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        boolean res = false;

        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            if (result.equals("IncorrectTelenum")) {
                                Toast.makeText(ForgetPasswordActivity.this, "Phone number does not match username", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ForgetPasswordActivity.this, "Phone number match", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgetPasswordActivity.this,ChangePasswordActivity.class);
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
                params.put("telenum", telenum);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public void ForgetToHome(View view){
        Intent intent = new Intent(ForgetPasswordActivity.this,MainActivity.class);
        startActivity(intent);
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CODE_REPEAT) {
                btn_check.setEnabled(true);
                btn_submit.setEnabled(true);
                tm.cancel();//取消任务
                tt.cancel();//取消任务
                TIME = 60;//时间重置
                btn_check.setText("Get again");
            }else {
                btn_check.setText(TIME + "s to get again");
            }
        }
    };

    //回调
    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    toast("Verification succeed");
                    ForgetPasswordRequest(username,phone);

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){       //获取验证码成功
                    toast("Get verification code succeed");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                    //返回支持发送验证码的国家列表
                }
            }else{//错误等在这里（包括验证失败）
                //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                ((Throwable)data).printStackTrace();
                String str = data.toString();
                //toast(str);
                toast("Verify Code Error!");
            }
        }
    };
    //弹窗确认下发
    private void alterWarning() {
        //构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hint"); //设置标题
        builder.setMessage(phone + " will get a verify code"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                //通过sdk发送短信验证（请求获取短信验证码，在监听（eh）中返回）
                SMSSDK.getVerificationCode(country, phone);
                //做倒计时操作
                Toast.makeText(ForgetPasswordActivity.this, "Has sent", Toast.LENGTH_SHORT).show();
                btn_check.setEnabled(false);
                btn_submit.setEnabled(true);
                tm = new Timer();
                tt = new TimerTask() {
                    @Override
                    public void run() {
                        hd.sendEmptyMessage(TIME--);
                    }
                };
                tm.schedule(tt,0,1000);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ForgetPasswordActivity.this, "Has canceled" , Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.check:
                phone = et_phonenum.getText().toString().trim().replaceAll("/s","");
                if (!TextUtils.isEmpty(phone)) {
                    //定义需要匹配的正则表达式的规则
                    String REGEX_MOBILE_SIMPLE =  "[1][358]\\d{9}";
                    //把正则表达式的规则编译成模板
                    Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
                    //把需要匹配的字符给模板匹配，获得匹配器
                    Matcher matcher = pattern.matcher(phone);
                    // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
                    if (matcher.find()) {//匹配手机号是否存在
                        alterWarning();

                    } else {
                        toast("Phone number's format error ");
                    }
                } else {
                    toast("Please input phone number");
                }
                break;
            case R.id.button7:
                //获得用户输入的验证码
                String code = et_verifycode.getText().toString().replaceAll("/s","");
                if (!TextUtils.isEmpty(code)) {//判断验证码是否为空
                    //验证
                    SMSSDK.submitVerificationCode( country,  phone,  code);
                }else{//如果用户输入的内容为空，提醒用户
                    toast("please input verify code");
                }
                break;
        }
    }

    //销毁短信注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调接口registerEventHandler必须和unregisterEventHandler配套使用，否则可能造成内存泄漏。
        SMSSDK.unregisterEventHandler(eh);
    }
}
