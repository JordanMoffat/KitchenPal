package io.moffat.kitchenpal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class BarcodeScanner extends Activity implements ZXingScannerView.ResultHandler {
    private  ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume(){
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }

    public void handleResult(Result rawResult){


        Intent intent = new Intent(BarcodeScanner.this, AddItem.class);
        intent.putExtra("flag", "barcode");
        intent.putExtra("barcode", rawResult.getText());
        startActivity(intent);

       // Toast.makeText(getApplicationContext(), rawResult.getText(),
         //       Toast.LENGTH_SHORT).show();

    }
}
