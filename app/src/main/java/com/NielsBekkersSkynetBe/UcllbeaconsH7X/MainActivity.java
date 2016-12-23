package com.NielsBekkersSkynetBe.UcllbeaconsH7X;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
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
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String USER_AGENT = "Mozilla/5.0";

    ProgressDialog progress;

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.UNKNOWN, android.graphics.Color.rgb(224, 0, 73));
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            progress = new ProgressDialog(this);
            progress.setTitle("Downloading");
            progress.setMessage("Fetching beacons...");
            progress.setCancelable(false);

            progress.show();

            getBeaconsFromWeb();

        } catch (Exception ex) {
            System.err.println(ex);
        }

        setContentView(R.layout.activity_main);

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
            if(proximityContentManager != null) {
                proximityContentManager.startContentUpdates();
            }
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
                        try {
                            parseBeacons(response);
                        } catch (JSONException ex) {
                            System.err.println(ex.getMessage());
                        }
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

    private void parseBeacons(String beaconsString) throws JSONException {
        JSONObject jObject = new JSONObject(beaconsString);
        JSONArray beacons = jObject.getJSONArray("public_beacons");
        Vector<BeaconDevice> beaconList = new Vector<>();
        for (int i=0; i < beacons.length(); i++)
        {
            try {
                BeaconDevice bd = new BeaconDevice();
                JSONObject beacon = beacons.getJSONObject(i);
                bd.setMajor(beacon.getInt("major"));
                bd.setMinor(beacon.getInt("minor"));
                bd.setUUID(beacon.getString("UUID"));
                bd.setName(beacon.getString("name"));
                bd.setLocationTitle(beacon.getString("locationTitle"));
                bd.setLocationDescription(beacon.getString("locationDescription"));
                bd.setImageUrl(beacon.getString("image"));

                beaconList.add(bd);
            } catch (JSONException e) {
                Log.e("ParseError", "Error parsing json");
            }
        }
        progress.dismiss();
        initializeBeacons(beaconList);
    }

    private void initializeBeacons(final Vector<BeaconDevice> beacons) {
        if (!beacons.isEmpty()) {
            ArrayList<BeaconID> list = new ArrayList<>();
            for(BeaconDevice bd : beacons) {
                try {
                    list.add(new BeaconID(bd.getUUID(), bd.getMajor(), bd.getMinor()));
                } catch (Exception ex) {
                    Log.e("Invalid beacon", "Invalid beacon detected, discarding...");
                }
            }

            proximityContentManager = new ProximityContentManager(this,
                    list, new EstimoteCloudBeaconDetailsFactory());
            proximityContentManager.setListener(new ProximityContentManager.Listener() {
                @Override
                public void onContentChanged(Object content) {
                    String text;
                    Integer backgroundColor;
                    Integer textColor = android.graphics.Color.WHITE;
                    if (content != null) {
                        EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                        BeaconDevice bd = getBeaconInfoFromList(beaconDetails, beacons);
                        if(bd != null) {
                            WidgetProvider w = new WidgetProvider();
                            w.setBeacon(bd);
                            try {
                                if(bd.getImageUrl().equals("")) {
                                    throw new Exception("No url linked to beacon");
                                }
                                UrlImageViewHelper.setUrlDrawable((ImageView) findViewById(R.id.imageView), "https://www.kuleuven.be/studentenvoorzieningen/gezondheid/images/ucll-logo-cmyk.png", R.drawable.loading);
                            } catch (Exception ex) {
                                Log.e("Error getting picture", ex.toString());
                                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.ucll);
                            }

                            text = "Welkom in " + bd.getKeyLocationTitle() +"\n\rHier vind je: "+bd.getLocationDescription();
                            backgroundColor = android.graphics.Color.rgb(224, 0, 73); //BACKGROUND_COLORS.get(Color.UNKNOWN);
                            textColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                        } else {
                            text = "Geen overeenkomende info over " + beaconDetails.getBeaconName();
                            backgroundColor = BACKGROUND_COLOR_NEUTRAL; //BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                        }
                    } else {
                        text = "Begeef je naar een lokaal om hier informatie over te krijgen";
                        backgroundColor = null;
                    }
                    ((TextView) findViewById(R.id.textView)).setText(text);
                    ((TextView) findViewById(R.id.textView)).setTextColor(textColor);
                    findViewById(R.id.relativeLayout).setBackgroundColor(
                            backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);

                }
            });
            proximityContentManager.startContentUpdates();
        }
    }
    private BeaconDevice getBeaconInfoFromList(EstimoteCloudBeaconDetails beacon, Vector<BeaconDevice> beacons) {
        for(BeaconDevice bd : beacons) {
            if(bd.getName().equals(beacon.getBeaconName())) {
                return bd;
            }
        }
        return null;
    }
}
