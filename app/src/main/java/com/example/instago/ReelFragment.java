package com.example.instago;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;

public class ReelFragment extends Fragment {
    String URL = "NULL";
    VideoView videoReelView;
    EditText et_getReelLink;
    Button btn_getReel;
    Button btn_downloadReel;
    private MediaController mediaController;
    String reelurl = "1";
    private Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reel_fragment, null);

        et_getReelLink = v.findViewById(R.id.getReellink);
        btn_getReel = v.findViewById(R.id.getReelButton);
        btn_downloadReel = v.findViewById(R.id.downloadReel);
        videoReelView = v.findViewById(R.id.particularreel);

        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoReelView);

        btn_getReel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL = et_getReelLink.getText().toString();
                if(et_getReelLink.equals(null)) {
                    Toast.makeText(getContext(), "Enter URL please", Toast.LENGTH_SHORT).show();
                }
                else {
                    String res2 = StringUtils.substringBefore(URL, "/?");
                    URL += res2 + "/?__a=1";
                    processData();
                }
            }
        });

        btn_downloadReel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!reelurl.equals("1")) {
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    request.setTitle("Downloaded");
                    request.setDescription(".......");
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, ""+System.currentTimeMillis()+".mp4");
                    DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                    Toast.makeText(getContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "No content found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void processData() {
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                MainUrl mainUrl = gson.fromJson(response, MainUrl.class);
                reelurl = mainUrl.getGraphql().getShortcode_media().getVideo_url();
                uri = Uri.parse(reelurl);
                videoReelView.setMediaController(mediaController);
                videoReelView.setVideoURI(uri);
                videoReelView.requestFocus();
                videoReelView.start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "There was an error getting the desired content", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
