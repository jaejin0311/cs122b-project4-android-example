package edu.uci.ics.fabflixmobile.ui.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.databinding.ActivitySearchBinding;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListActivity;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private EditText search_title;
    private final String host = "10.0.2.2";
    private final String port = "8443";
    private final String domain = "cs122b_project1-yolo";
    private final String baseURL = "https://" + host + ":" + port + "/" + domain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        search_title = binding.searchTitle;
        final Button searchButton = binding.search;

        searchButton.setOnClickListener(view -> performSearch());
    }

    @SuppressLint("SetTextI18n")
    public void performSearch() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest searchRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/auto-complete", // Ad// just the endpoint as needed
                response -> {
                    Log.d("search.success", response);

                    // TODO: Parse the response and handle the search results accordingly
                    // For now, let's just log the response and proceed to the MovieListActivity

                    finish();
                    Intent movieListIntent = new Intent(SearchActivity.this, MovieListActivity.class);
                    startActivity(movieListIntent);
                },
                error -> {
                    Log.d("search.error", error.toString());
                    // Handle the error, e.g., display an error message to the user
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("query", search_title.getText().toString());
                return params;
            }
        };

        queue.add(searchRequest);
    }
}
