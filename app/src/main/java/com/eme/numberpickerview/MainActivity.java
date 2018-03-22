package com.eme.numberpickerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.eme.numberpickerlibrary.NumberPickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberPickerView npvnumber = (NumberPickerView) findViewById(R.id.npv_number);
        npvnumber
                .setCurrentInventory(50000)
                .setMinDefaultNum(0)
                .setCurrentNum(0)
                .setMoq(0)
                .setmOnClickInputListener(new NumberPickerView.OnClickInputListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                        Log.e("inventory", inventory+"");
                        Toast.makeText(MainActivity.this,"超过最大库存",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningMinInput(int minValue) {
                        Log.e("minValue", minValue+"");
                        Toast.makeText(MainActivity.this,"低于最小设定值",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningMaxInput(int maxValue) {
                        Log.e("maxValue", maxValue+"");
                        Toast.makeText(MainActivity.this,"超过最大限制量",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void subListener(int currentValue) {
                        Log.e("sub", currentValue+"");
                    }

                    @Override
                    public void addListener(int currentValue) {
                        Log.e("add", currentValue+"");
                    }

                    @Override
                    public void editListener(int currentValue) {
                        Log.e("edit", currentValue+"");
                    }
                });


    }
}
