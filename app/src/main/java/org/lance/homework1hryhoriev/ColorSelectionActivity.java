package org.lance.homework1hryhoriev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

public class ColorSelectionActivity extends AppCompatActivity {
    public static final String TAG = "ColorSelectionActivity";

    public static final String CHOSEN_COLOR = "org.lance.homework1hryhoriev";
    public static final int CHANGE_BACKGROUND_REQUEST = 0;
    public static final int CHANGE_BUTTONS_REQUEST = 1;
    public static final int CHANGE_FONT_REQUEST = 2;

    private Spinner spinSelectColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_selection);
        spinSelectColor = findViewById(R.id.spinSelectColor);
    }

    public void onOkButtonClicked(View v) {
        Intent resultIntent = new Intent();
        Log.d(TAG, "onOkButtonClicked: spinSelectColor position = " + spinSelectColor.getSelectedItemPosition());
        resultIntent.putExtra(CHOSEN_COLOR, spinSelectColor.getSelectedItemPosition());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onCancelButtonClicked(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
