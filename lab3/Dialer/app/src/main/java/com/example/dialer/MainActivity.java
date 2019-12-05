package com.example.dialer;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText)this.findViewById(R.id.editText1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void click(View view){
        String str = et.getText().toString();
        str += view.getTag().toString();
        et.setText(str);
    }

    public void dial(View view){
        String str = et.getText().toString();
        if((str != null) && !(str.trim().equals(""))){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + str));
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "请拨打号码按钮", Toast.LENGTH_LONG).show();
        }
    }

    public void del(View view){
        String str = et.getText().toString();
        if((str != null) && !(str.trim().equals(""))){
            str = str.substring(0, str.length() - 1);
            et.setText(str);
        }
    }

}

