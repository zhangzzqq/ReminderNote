package com.example.zq.remindernote.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zq.remindernote.Base.BaseActivity;
import com.example.zq.remindernote.R;

public class MainActivity2 extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        TextView tvClick = (TextView) findViewById(R.id.tvClick);
        final EditText etContent = (EditText) findViewById(R.id.etContent);


        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strResult = etContent.getText().toString().replaceAll(" ", "");
                showShareDialog();
            }
        });


    }

    /**
     * 弹出分享列表
     */
    private void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setTitle("选择分享类型");
        builder.setItems(new String[]{"邮件", "短信", "其他"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                switch (which) {
                    case 0:    //邮件
                        sendMail("http://www.google.com.hk/");
                        break;

                    case 1:    //短信
                        sendSMS("http://www.google.com.hk/");
                        break;

                    case 3:    //调用系统分享
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                        intent.putExtra(Intent.EXTRA_TEXT, "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + "http://www.google.com.hk/");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "share"));
                        break;

                    default:
                        break;
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     * 发送邮件
     *
     * @@param emailBody
     */
    private void sendMail(String emailUrl) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");

        String emailBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + emailUrl;
        //邮件主题
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "便签");
        //邮件内容
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);

        startActivityForResult(Intent.createChooser(email, "请选择邮件发送内容"), 1001);
    }


    /**
     * 发短信
     */
    private void sendSMS(String webUrl) {
        String smsBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + webUrl;
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        //短信内容
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }


}

