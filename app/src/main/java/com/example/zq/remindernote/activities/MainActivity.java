package com.example.zq.remindernote.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.fragment.HistoryFragment;
import com.example.zq.remindernote.fragment.TodayFragment;
import com.example.zq.remindernote.fragment.TomorrowFragment;
import com.example.zq.remindernote.fragment.YesterdayFragment;
import com.example.zq.remindernote.interfaces.SaveData;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity implements SaveData {
    //测试
    private FragmentTabHost tabHost;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        tabHost = (FragmentTabHost) findViewById(R.id.fragment_tabhost);
        //关联碎片
        tabHost.setup(this, getSupportFragmentManager(), R.id.fragment_tabcontent);
        //添加碎片
        tabHost.addTab(tabHost.newTabSpec("yesterdaytab").setIndicator(getView(0)), YesterdayFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("todaytab").setIndicator(getView(1)), TodayFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tomorrowtab").setIndicator(getView(2)), TomorrowFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tabhistory").setIndicator(getView(3)), HistoryFragment.class, null);
        //去掉间隔图片
        tabHost.getTabWidget().setDividerDrawable(null);

    }

    private void initData() {

        tabHost.setCurrentTab(1);
        LitePal.getDatabase();//创建表
    }


    //得到视图 ,关联对应的tabhost
    private View getView(int position) {
        View  v = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        ImageView  tabImg = (ImageView) v.findViewById(R.id.tab_img);
        TextView  tabText = (TextView) v.findViewById(R.id.tab_name);

        switch (position) {
            case 0:
                tabImg.setBackgroundResource(R.drawable.bg_yesterday);
                tabText.setText(R.string.yesterday);
                break;
            case 1:
                tabImg.setBackgroundResource(R.drawable.bg_today);
                tabText.setText(R.string.today);
                break;
            case 2:
                tabImg.setBackgroundResource(R.drawable.bg_tomorrow);
                tabText.setText(R.string.tomorrow);
                break;
            case 3:
                tabImg.setBackgroundResource(R.drawable.bg_history);
                tabText.setText(R.string.history);
                break;
        }
        return v;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    /**
     * 弹出分享列表
     * @param content
     */
    private void showShareDialog(final String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("选择分享类型");
        builder.setItems(new String[]{"邮件", "短信", "其他"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                switch (which) {
                    case 0:    //邮件
                        sendMail(content);
                        break;

                    case 1:    //短信
                        sendSMS(content);
                        break;

                    case 3:    //调用系统分享
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                        intent.putExtra(Intent.EXTRA_TEXT, content);
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

        String emailBody =  emailUrl;
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
        String smsBody =  webUrl;
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        //短信内容
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }


    @Override
    public void saveContent(String content) {

        showShareDialog(content);
    }


    public int getCurrentTab(){

        return  tabHost.getCurrentTab();

    }


}