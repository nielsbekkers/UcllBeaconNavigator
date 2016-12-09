package com.NielsBekkersSkynetBe.UcllbeaconsH7X;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.NielsBekkersSkynetBe.UcllbeaconsH7X.estimote.BeaconID;
import com.NielsBekkersSkynetBe.UcllbeaconsH7X.estimote.EstimoteCloudBeaconDetails;
import com.NielsBekkersSkynetBe.UcllbeaconsH7X.estimote.EstimoteCloudBeaconDetailsFactory;
import com.NielsBekkersSkynetBe.UcllbeaconsH7X.estimote.ProximityContentManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String USER_AGENT = "Mozilla/5.0";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("40E0A26E-F512-4DC0-822A-438CAF606286", 56034, 53222),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 60369, 59394),
                        new BeaconID("2009D368-B855-47C9-9D33-F638ED0D7465", 47541, 3683)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    text = "You're in " + beaconDetails.getBeaconName() + "'s range!";
                    backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                } else {
                    text = "No beacons in range.";
                    backgroundColor = null;
                }
                ((TextView) findViewById(R.id.textView)).setText(text);
                findViewById(R.id.relativeLayout).setBackgroundColor(
                        backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);
            }
        });
        try {
            this.getBeaconsFromWeb();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }

    private void getBeaconsFromWeb() throws Exception {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://beacons.alxb.be/fetch.php/beacons";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG+"/HttpGet", "Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG+"/HttpGet", "HTTP get error! "+error.getMessage());
            }
        });
        queue.add(stringRequest);

    }
}
