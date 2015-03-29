package com.android.will.waikatopapers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.will.waikatopapers.R;
import com.android.will.waikatopapers.model.Paper;

import java.util.List;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 29/03/15.
 */
public class PaperListAdapter extends ArrayAdapter<Paper> {
    private LayoutInflater layout_inflater;
    public PaperListAdapter(Context context, int resource, int textViewResourceId, List<Paper> objects) {
        super(context, resource, textViewResourceId, objects);
        layout_inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layout_inflater.inflate(R.layout.paper_list_item, parent, false);

            holder = new ViewHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.paper_thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.paper_title);
            holder.price = (TextView) convertView.findViewById(R.id.paper_lecturer);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Paper paper = this.getItem(position);

        holder.thumbnail.setImageResource(paper.getThumbnail_id());
        holder.title.setText(paper.getTitle());
        holder.price.setText(paper.getLecturer());

        return convertView;
    }

    static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView price;
    }
}
