package org.stevej.android.propertyfinder.adapters;

import java.util.List;

import org.stevej.android.propertyfinder.R;
import org.stevej.android.propertyfinder.model.Property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PropertyListAdapter extends ArrayAdapter<Property> {
	private LayoutInflater layout_inflater;

	public PropertyListAdapter(Context context, int item_layout_id, int default_text_id, List<Property> properties) {
		super(context, item_layout_id, default_text_id, properties);
		layout_inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = layout_inflater.inflate(R.layout.property_list_item, null);

			holder = new ViewHolder();
			holder.thumbnail = (ImageView) convertView.findViewById(R.id.property_thumbnail);
			holder.title = (TextView) convertView.findViewById(R.id.property_title);
			holder.price = (TextView) convertView.findViewById(R.id.property_price);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Property property = this.getItem(position);

		holder.thumbnail.setImageResource(property.getThumbnailID());
		holder.title.setText(property.getTitle());
		holder.price.setText(property.getPrice());

		return convertView;
	}

	static class ViewHolder {
		ImageView thumbnail;
		TextView title;
		TextView price;
	}

}
