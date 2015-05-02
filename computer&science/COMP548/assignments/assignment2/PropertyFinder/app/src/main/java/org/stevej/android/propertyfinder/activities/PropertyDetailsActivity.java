package org.stevej.android.propertyfinder.activities;

import org.stevej.android.propertyfinder.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PropertyDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_property_details);

		Intent intent = getIntent();
		String title = intent.getStringExtra("property_title");
		String price = intent.getStringExtra("property_price");

		TextView title_view = (TextView) findViewById(R.id.property_details_title);
		TextView price_view = (TextView) findViewById(R.id.property_details_price);

		title_view.setText(title);
		price_view.setText(price);


		if (savedInstanceState != null) {
		}
	}


}
