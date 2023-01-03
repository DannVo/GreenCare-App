package com.example.iotapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import kotlin.jvm.internal.Intrinsics;


public class QrcodeActivity extends AppCompatActivity {
    private CodeScanner codeScanner;
    private TextView tvScan;


    public static final int CAMERA_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.qrcode_activity);



        this.setupPermissions();
        this.codeScanner();
    }

    public void codeScanner(){
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this,scannerView);
        tvScan = findViewById(R.id.tvScan);



        codeScanner.setCamera(-1);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);

        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);

//        codeScanner.setDecodeCallback(result -> {
//            this.runOnUiThread(() -> Toast.makeText(this, result.getText(), Toast.LENGTH_LONG).show());
//            tvScan.setText(result.getText());
////                goBack(scan);
//
//        });
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(QrcodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        tvScan.setText(result.getText());
                        StringToJSON(result.getText());
                    }
                });
            }
        });

        codeScanner.setErrorCallback(result -> {
            this.runOnUiThread(() -> Toast.makeText(this, "Error has been found!", Toast.LENGTH_LONG).show());

//                goBack(scan);

        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

    }

    public void StringToJSON(String str){



        String json = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";


        try {

            JSONObject obj = new JSONObject(json);

            Log.d("My App", obj.toString());

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
    }

    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private final void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission((Context)this, "android.permission.CAMERA");
        if (permission != 0) {
            this.makeRequest();
        }

    }

    private final void makeRequest() {
        ActivityCompat.requestPermissions(this,new String[]{"android.permission.CAMERA"}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length == 0 || grantResults[0] != 0) {
                    Toast.makeText((Context)this, (CharSequence)"You need the camera permission to be able to connect", Toast.LENGTH_SHORT).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }




//    public final class MainActivity extends AppCompatActivity {
//        private HashMap _$_findViewCache;
//
//
//
////        private final void codeScanner() {
////            this.codeScanner = new CodeScanner((Context)this, (CodeScannerView)this._$_findCachedViewById(id.scanner_view));
////
////
////
////
////
////            codeScanner.setErrorCallback((ErrorCallback)(new MainActivity$codeScanner$$inlined$apply$lambda$2(this)));
////            ((CodeScannerView)this._$_findCachedViewById(id.scanner_view)).setOnClickListener((OnClickListener)(new OnClickListener() {
////                public final void onClick(View it) {
////                    MainActivity.access$getCodeScanner$p(MainActivity.this).startPreview();
////                }
////            }));
////        }
//
//
//
//
//
//        private final void makeRequest() {
//            ActivityCompat.requestPermissions((Activity)this, new String[]{"android.permission.CAMERA"}, 101);
//        }
//
//        public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
//            Intrinsics.checkNotNullParameter(permissions, "permissions");
//            Intrinsics.checkNotNullParameter(grantResults, "grantResults");
//            switch(requestCode) {
//                case 101:
//                    if (grantResults.length == 0 || grantResults[0] != 0) {
//                        Toast.makeText((Context)this, (CharSequence)"You need the camera permission to be able to connect", 0).show();
//                    }
//                default:
//            }
//        }
//
//        // $FF: synthetic method
//        public static final CodeScanner access$getCodeScanner$p(MainActivity $this) {
//            CodeScanner var10000 = $this.codeScanner;
//            if (var10000 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("codeScanner");
//            }
//
//            return var10000;
//        }
//
//        // $FF: synthetic method
//        public static final void access$setCodeScanner$p(MainActivity $this, CodeScanner var1) {
//            $this.codeScanner = var1;
//        }
//
//        public View _$_findCachedViewById(int var1) {
//            if (this._$_findViewCache == null) {
//                this._$_findViewCache = new HashMap();
//            }
//
//            View var2 = (View)this._$_findViewCache.get(var1);
//            if (var2 == null) {
//                var2 = this.findViewById(var1);
//                this._$_findViewCache.put(var1, var2);
//            }
//
//            return var2;
//        }
//
//        public void _$_clearFindViewByIdCache() {
//            if (this._$_findViewCache != null) {
//                this._$_findViewCache.clear();
//            }
//
//        }
//    }



}