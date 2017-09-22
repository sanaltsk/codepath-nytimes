package com.codepath.week1.nytimessearch.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.week1.nytimessearch.R;
import com.codepath.week1.nytimessearch.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by sanal on 9/21/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(@NonNull Context context, List<Article>articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data item
        Article article = this.getItem(position);
        //check if existing view is recycled
        //if not inflate the layout
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result,parent,false);
        }

        //once inflated,
        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivImage);

        //clear out recycled image
        imageView.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadLine());

        String thumbnail = article.getThumbNail();
        if(!TextUtils.isEmpty(thumbnail)) {
            //populate the thumbnail image
            Picasso.with(getContext()).load(thumbnail)
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
                    .into(imageView);
        }
        return convertView;
    }
}
