package com.codepath.week1.nytimessearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.week1.nytimessearch.R;
import com.codepath.week1.nytimessearch.activities.ArticleActivity;
import com.codepath.week1.nytimessearch.model.Article;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by sanal on 9/21/17.
 */

public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder>{
    private List<Article> articles;
    private Context context;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView tvTitle;
        public ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                Article article = articles.get(position);
                Intent intent = new Intent(getContext(), ArticleActivity.class);
                intent.putExtra("article", article);
                getContext().startActivity(intent);
            }
        }
    }

    public ArticleArrayAdapter(Context context, List<Article>articles) {
        this.context = context;
        this.articles = articles;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(articles.size()>0) {
            Article article = articles.get(position);

            TextView tvTitle = holder.tvTitle;
            tvTitle.setText(article.getHeadLine());

            ImageView ivImage = holder.ivImage;
            ivImage.setImageResource(0);

            String thumbnail = article.getThumbNail();
            if (!TextUtils.isEmpty(thumbnail)) {
                Glide.with(context)
                        .load(thumbnail)
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(ivImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
