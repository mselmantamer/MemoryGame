package com.example.gameapplication3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
public class HolderAdapter extends ArrayAdapter<Score> implements Filterable {

    private final int listItemLayout;
    Context context;

    public HolderAdapter(Context context, int layoutId, ArrayList<Score> itemList) {
        super(context, layoutId, itemList);
        listItemLayout = layoutId;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Score item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(listItemLayout, parent, false);
            viewHolder.scoreRank = convertView.findViewById(R.id.scoreRank);
            viewHolder.scoreName = convertView.findViewById(R.id.scoreName);
            viewHolder.scoreText = convertView.findViewById(R.id.scoreText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.scoreRank.setText(item.getRank());
        viewHolder.scoreName.setText(item.getName());
        viewHolder.scoreText.setText(item.getScore());

        return convertView;
    }

    private static class ViewHolder {
        TextView scoreRank, scoreName, scoreText;
    }
}
