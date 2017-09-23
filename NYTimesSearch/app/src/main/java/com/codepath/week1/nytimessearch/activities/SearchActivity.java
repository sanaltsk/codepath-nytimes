package com.codepath.week1.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.week1.nytimessearch.R;
import com.codepath.week1.nytimessearch.adapter.ArticleArrayAdapter;
import com.codepath.week1.nytimessearch.model.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    Button etButton;
    GridView etResults;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    String beginDate;
    String sortOrder;
    Boolean cbArts, cbFashion, cbSports;
    String fq_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    private void setupViews() {
        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.etResults);
        if(articles==null) {
            articles = new ArrayList<>();
        }

        etQuery = (EditText) findViewById(R.id.etQuery);
        etButton = (Button) findViewById(R.id.etSearch);
        adapter = new ArticleArrayAdapter(this, articles);
        rvArticles.setAdapter(adapter);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvArticles.setLayoutManager(gridLayoutManager);//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            this.startActivityForResult(intent, 2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2) {
            if(null != data) {
                beginDate = data.getStringExtra("begin_date");
                sortOrder = data.getStringExtra("order");

                cbArts = data.getBooleanExtra("arts", false);
                cbFashion = data.getBooleanExtra("fashion", false);
                cbSports = data.getBooleanExtra("sports", false);


                if(cbArts || cbSports || cbFashion) {
                    fq_search = "news_desk:(";
                    if (cbArts)
                        fq_search += "Arts,";
                    if (cbFashion)
                        fq_search += "Fashion, Style,";
                    if (cbSports)
                        fq_search += "Sports";
                    fq_search += ")";
                }
                Log.d("debug", beginDate+":"+sortOrder+":"+cbArts.toString()+":"+cbFashion.toString()+":"+cbSports.toString()+":"+fq_search);
            }
        }
    }

    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key","fbe95cf398c3486bba648665321509be");
        params.put("page",0);
        params.put("q",query);
        params.put("begin_date",beginDate);
        params.put("sort",sortOrder);
        params.put("fq",fq_search);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;
                try {
                    articles.clear();
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJSONArray(articleJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                Log.d("debug",articles.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("debug",responseString);
            }
        });
    }
}
