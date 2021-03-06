package windsekirun.qrreader;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import windsekirun.qrreader.db.TempResultAdapter;

@SuppressWarnings("ALL")
public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    public TempResultAdapter adapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 20) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(getResources().getColor(R.color.light_blue_800));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("QR Reader Test");
        toolbar.setTitleTextColor(0xffffffff);
        Button generate = (Button) findViewById(R.id.generate);
        Button capture = (Button) findViewById(R.id.read);
        Button license = (Button) findViewById(R.id.license);
        Button send = (Button) findViewById(R.id.serverSend);
        Button github = (Button) findViewById(R.id.github);
        setSupportActionBar(toolbar);
        generate.setOnClickListener(this);
        capture.setOnClickListener(this);
        license.setOnClickListener(this);
        send.setOnClickListener(this);
        github.setOnClickListener(this);
        adapter = new TempResultAdapter(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.generate) {
            startActivity(new Intent(MainActivity.this, GenerateActivity.class));
            finish();
        } else if (id == R.id.read) {
            startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            finish();
        } else if (id == R.id.license) {
            showLicenseDialog();
        } else if (id == R.id.serverSend) {
            startActivity(new Intent(MainActivity.this, SendActivity.class));
            finish();
        } else if (id == R.id.github) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/windsekirun/qrreader"));
            startActivity(i);
        }
    }

    public void showLicenseDialog() {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(this).inflate(R.layout.dialog, null, false);
        WebView wv = (WebView) v.findViewById(R.id.wv);
        wv.loadUrl("file:///android_asset/license.html");
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(v);
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ab.show();
    }
}
