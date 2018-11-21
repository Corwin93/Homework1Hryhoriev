package org.lance.homework1hryhoriev;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * B1
 * Variant B (RelativeLayout)
 * Variant 1 (Spinner)
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static final String EXTRA_BACKGROUND_COLOUR = "org.lance.homework1hryhoriev.mBackgroundColour";
    public static final String EXTRA_BUTTONS_COLOUR = "org.lance.homework1hryhoriev.mButtonsColour";
    public static final String EXTRA_FONT_COLOUR = "org.lance.homework1hryhoriev.mFontColour";
    public static final String EXTRA_PLAYER_POSITION = "org.lance.homework1hryhoriev.mCurrentPlayerPosition";
    public static final String EXTRA_IS_PLAYING = "org.lance.homework1hryhoriev.isPlaying";
    private Button btnBackgroundChange;
    private Button btnButtonsChange;
    private Button btnFontChange;
    private FloatingActionButton fab;
    private int mBackgroundColour = -1;
    private int mButtonsColour = -1;
    private int mFontColour = -1;
    private MediaPlayer mPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnBackgroundChange = findViewById(R.id.btnBackgroundChange);
        btnButtonsChange = findViewById(R.id.btnButtonsChange);
        btnFontChange = findViewById(R.id.btnFontChange);
        mPlayer = MediaPlayer.create(this, R.raw.alessia_here);

        fab = findViewById(R.id.btnPlay);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    pausePlayer();
                } else {
                    startPlayer();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        if (isPlaying) {
            pausePlayer();
        }
        super.onStop();
    }

    private void startPlayer() {
        mPlayer.start();
        isPlaying = true;
        fab.setImageResource(android.R.drawable.ic_media_pause);
    }
    private void pausePlayer() {
        mPlayer.pause();
        isPlaying = false;
        fab.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if((mBackgroundColour = savedInstanceState.getInt(EXTRA_BACKGROUND_COLOUR, -1)) != -1) {
            setBackgroundColour(mBackgroundColour);
        }
        if((mButtonsColour = savedInstanceState.getInt(EXTRA_BUTTONS_COLOUR, -1)) != -1) {
            setButtonsColour(mButtonsColour);
        }
        if((mFontColour = savedInstanceState.getInt(EXTRA_FONT_COLOUR, -1)) != -1) {
            setFontColour(mFontColour);
        }
        mPlayer.seekTo(savedInstanceState.getInt(EXTRA_PLAYER_POSITION, 0));
        if (savedInstanceState.getBoolean(EXTRA_IS_PLAYING)) {
            startPlayer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_BACKGROUND_COLOUR, mBackgroundColour);
        outState.putInt(EXTRA_BUTTONS_COLOUR, mButtonsColour);
        outState.putInt(EXTRA_FONT_COLOUR, mFontColour);
        outState.putInt(EXTRA_PLAYER_POSITION, mPlayer.getCurrentPosition());
        outState.putBoolean(EXTRA_IS_PLAYING, isPlaying);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onColourButtonClicked(View v) {
        int requestCode;
        switch (v.getId()) {
            case(R.id.btnBackgroundChange):
                requestCode = ColorSelectionActivity.CHANGE_BACKGROUND_REQUEST;
                break;
            case(R.id.btnButtonsChange):
                requestCode = ColorSelectionActivity.CHANGE_BUTTONS_REQUEST;
                break;
            case(R.id.btnFontChange):
                requestCode = ColorSelectionActivity.CHANGE_FONT_REQUEST;
                break;
                default:
                    requestCode = ColorSelectionActivity.CHANGE_BACKGROUND_REQUEST;
        }
        Log.d(TAG, "onColourButtonClicked: requestCode = " + requestCode);
        Intent activity = new Intent(this, ColorSelectionActivity.class);
        startActivityForResult(activity, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            int chosenColour = 0;
            if (data != null) {
                chosenColour = data.getIntExtra(ColorSelectionActivity.CHOSEN_COLOR, 0);
            }
            Log.d(TAG, "onActivityResult: chosenColour = " + chosenColour);
            Log.d(TAG, "onActivityResult: requestCode = " + requestCode);
            int preparedColour;
                switch (chosenColour) {
                    case (0):
                        preparedColour = Color.BLACK;
                        break;
                    case (1):
                        preparedColour = Color.BLUE;
                        break;
                    case (2):
                        preparedColour = Color.GREEN;
                        break;
                    case (3):
                        preparedColour = Color.CYAN;
                        break;
                    case (4):
                        preparedColour = Color.MAGENTA;
                        break;
                    case (5):
                        preparedColour = Color.RED;
                        break;
                        default:
                            preparedColour = Color.BLACK;
            }
            if(requestCode == ColorSelectionActivity.CHANGE_BACKGROUND_REQUEST) {
                setBackgroundColour(preparedColour);
            } else if(requestCode == ColorSelectionActivity.CHANGE_BUTTONS_REQUEST) {
                setButtonsColour(preparedColour);
            } else if(requestCode == ColorSelectionActivity.CHANGE_FONT_REQUEST) {
                setFontColour(preparedColour);
            }
        }
    }

    private void setBackgroundColour(int colour) {
        ViewGroup mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setBackgroundColor(colour);
        mBackgroundColour = colour;
    }

    private void setButtonsColour(int colour) {
        btnBackgroundChange.setBackgroundColor(colour);
        btnButtonsChange.setBackgroundColor(colour);
        btnFontChange.setBackgroundColor(colour);
        mButtonsColour = colour;
    }

    private void setFontColour(int colour) {
        btnBackgroundChange.setTextColor(colour);
        btnButtonsChange.setTextColor(colour);
        btnFontChange.setTextColor(colour);
        mFontColour = colour;
    }

    @Override
    protected void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }
}
