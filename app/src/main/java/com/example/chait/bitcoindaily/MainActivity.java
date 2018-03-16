package com.example.chait.bitcoindaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeScroll;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //Member variables
    Spinner mSpinner;
    TextView mCurrencyValue;

    //Constants
    final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mSpinner = findViewById(R.id.spinner);
        mCurrencyValue = findViewById(R.id.currencyTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.cur, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.selection_layout);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("BTCDaily", "Selected an item from dropdown list");
                Log.d("BTCDaily", "Now accessing API");
                getPrice(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            Log.d("BTCDaily", "No item selected from dropdown list");
            }
        });


    }

    private void getPrice(final String CUR){

        AsyncHttpClient client = new AsyncHttpClient();


        String URL = BASE_URL + CUR;
        client.get(URL, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.d("BTCDaily", "Success! Data received");
            currencyData data;
            data = currencyData.fromJSON(response);
            updateUI(data, CUR);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.d("BTCDaily", "Failure! Data not received" + errorResponse.toString());
            }
        });
    }

    private void updateUI(currencyData data, String CUR){
        if(CUR.equals("None"))
        {
            mCurrencyValue.setText(R.string.welcomeText);
        }
        else {
            String setPrice = "Price in " + CUR + " is " + data.mPrice;
            mCurrencyValue.setText(setPrice);
        }
    }
}
