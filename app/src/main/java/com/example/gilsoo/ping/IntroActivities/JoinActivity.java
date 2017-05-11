package com.example.gilsoo.ping.IntroActivities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Item.UserInfo;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.Join;
import com.example.gilsoo.ping.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends Activity {
    private NetworkService networkService = null;

    private Button btnJoin;
    private EditText id,pw,repw,name;
    private TextView birth,birthday;
    private DatePicker date_picker;
    private RadioGroup rg;
    //private RadioButton male,female;
    private RadioButton gender;
    int year,month,day;
    static final int DATE_DIALOG_ID = 100;
    RadioButton radio_woman,radio_man;

//    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        birthday = (TextView)findViewById(R.id.join_birthday);
        id = (EditText)findViewById(R.id.join_id);
        pw = (EditText)findViewById(R.id.join_pw);
        repw = (EditText)findViewById(R.id.join_re_pw);
        name = (EditText)findViewById(R.id.join_name);
        rg = (RadioGroup)findViewById(R.id.radio_gender);
        radio_woman = (RadioButton)findViewById(R.id.gender_female);
        radio_man = (RadioButton)findViewById(R.id.gender_male);
        btnJoin = (Button)findViewById(R.id.btnJoin);

        setCurrentDate();
        addButtonListener();
//        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(this, InitActivity.class));
    }


    private void addButtonListener() {
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = (rg.getCheckedRadioButtonId() == R.id.gender_male? "male" : "female");
                joinToPing(id.getText().toString(), gender, name.getText().toString(), pw.getText().toString()
                        , repw.getText().toString(), birth.getText().toString());
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.gender_male) {
                    radio_man.setBackgroundResource(R.drawable.appjam_visual_man_on);
                    radio_woman.setBackgroundResource(R.drawable.appjam_visual_woman_off);
                } else if (checkedId == R.id.gender_female) {
                    radio_man.setBackgroundResource(R.drawable.appjam_visual_man_off);
                    radio_woman.setBackgroundResource(R.drawable.appjam_visual_woman_on);

                }
            }
        });
    }

    private void setCurrentDate() {
        birth = (TextView)findViewById(R.id.join_birth);
        date_picker = (DatePicker)findViewById(R.id.date_picker);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        birth.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
        ((View)date_picker).setVisibility(View.GONE);
        date_picker.init(year, month, day, null);

    }

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID: return new DatePickerDialog(this, datePickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            year = selectedYear;
            month  = selectedMonth;
            day = selectedDay;
            birth.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
            date_picker.init(year, month, day, null);

            ((View)date_picker).setVisibility(View.GONE);
        }
    };

    public void joinToPing(String id, String gender, String name, String pw, String repw, String birth){
        if(networkService == null)
            networkService = ApplicationController.getInstance().getNetworkService();
        Call<UserInfo> callJoin = networkService.newJoin(new Join(id, gender, name, pw, repw, birth));
        callJoin.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "JoinToPing() : onResponse() : isSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "JoinToPing() : onResponse() : isSuccessful() : " + response.code());

                    UserInfo userInfo = response.body();
                    Log.d("gilsoo_Retro", "loginToPing() : onResponse() : Join : userinfo : " + userInfo.getUser_id() +" "+ userInfo.getUser_name()+" "+ userInfo.getToken());
                    CommonData.user = userInfo;

                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                    startActivity(intent);
                    InitActivity.initAct.finish();
                    finish();
                }else{
                    Log.d("gilsoo_Retro", "JoinToPing() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "JoinToPing() : onResponse() : isNotSuccessful() : " + response.code());

                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("gilsoo_Retro" , "JoinToPing() : onFailure() : " + t.getMessage());

                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
                InitActivity.initAct.finish();
                finish();
            }
        });
    }
}