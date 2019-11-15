package com.example.cats.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cats.R;
import com.example.cats.activities.MainActivity;
import com.example.cats.adapters.CatAdapter;
import com.example.cats.database.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchRecyclerFragment extends Fragment {

    private boolean result = true;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private final CatAdapter catAdapter = new CatAdapter();
    private TextView notice;

    public SearchRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_recycler, container, false);
        recyclerView = view.findViewById(R.id.rv_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        catAdapter.setData(Database.catSearch);
        recyclerView.setAdapter(catAdapter);

        notice = view.findViewById(R.id.notice);
        notice.setText("Enter cat name to search.");
        if(Database.catSearch.size() > 0){
            notice.setVisibility(View.GONE);
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(Database.catSearch.size() > 0){
                                    notice.setVisibility(View.GONE);
                                }

                                if(!result){
                                    notice.setText("No result");
                                }

                                catAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                notice.setVisibility(View.VISIBLE);
                notice.setText("Searching...");
                getCatId(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;

    }

    // Get all cat which name contain the string searched without image url
    public void getCatId(final String query){
        Database.catSearch.clear();

        String url = "https://api.thecatapi.com/v1/breeds/search?q=" + query;

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("[]")){
                    result = false;
                } else {
                    result = true;
                }
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // get id of each cat found and search their information and image
                        // with method "getCatById()"
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        getCatById(id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error occur: " + error);
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

    // Get cat individually by their id with image url
    public void getCatById(String catId){
        String url = "https://api.thecatapi.com/v1/images/search?breed_id=" + catId;

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Database.addCatSearch(response);
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);

        requestQueue.add(stringRequest);
    }


    // This is just an example of a way that the Fragment can communicate with the parent Activity.
    // Specifically, this is using a method belonging to the parent.
    @Override
    public void onResume() {
        super.onResume();
        MainActivity parent = (MainActivity) getActivity();
    }
}