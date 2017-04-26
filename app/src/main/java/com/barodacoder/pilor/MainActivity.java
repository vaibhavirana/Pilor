package com.barodacoder.pilor;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barodacoder.pilor.utils.Category;
import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.utils.RecyclerItemClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends ActivityBase
{
    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView rvCategory;

    private ArrayList<Category> alCategory;

    private AdapterCategories adpCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initData();

        initUi();


    }


    protected void initData()
    {
        super.initData();

        alCategory = new ArrayList<>();
    }

    private void initUi()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                //getCategoryList();
            }
        });

        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        //rvCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rvCategory.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvCategory, new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                if(alCategory.get(position).getAlSubCategory().size() > 0)
                {
                   // goToSubCategoryScreen(alCategory.get(position));
                }
                else
                {
                   // goToCreateJobScreen(alCategory.get(position));
                }
                //goToStoryDetailScreen(aluserList.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position)
            {

            }
        }));

        adpCategory = new AdapterCategories();
        rvCategory.setAdapter(adpCategory);
    }

    public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder>
    {
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            private ImageView ivImage;

            private TextView tvCatName;

            public MyViewHolder(View view)
            {
                super(view);

                ivImage = (ImageView) view.findViewById(R.id.ivImage);

                tvCatName = (TextView) view.findViewById(R.id.tvCatName);
                tvCatName.setTypeface(appData.getFontRegular());
            }
        }

        public AdapterCategories()
        {
            //this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

            //itemView.setOnClickListener(mOnClickListener);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
           /* if(!alCategory.get(position).getCategoryImage().equals(""))
                Glide.with(getApplicationContext()).load(alCategory.get(position).getCategoryImage()).placeholder(R.drawable.example_cell).into(holder.ivImage);

            holder.tvCatName.setText(alCategory.get(position).getCategoryName());*/
        }

        @Override
        public int getItemCount() {

            return 5;
            //return alCategory.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_setting)
        {
            //goToSettingScreen();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getCategoryList()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());

        client.post(AppConstants.URL_LIST_CATEGORY, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                swipeRefresh.setRefreshing(false);

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "CATEGORY RESPONSE : " + response);

                    alCategory.clear();

                    alCategory.addAll(ParseJson.parseCategoryList(response));

                    adpCategory.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                swipeRefresh.setRefreshing(false);

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "CATEGORY RESPONSE : FAILED : " + response);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
