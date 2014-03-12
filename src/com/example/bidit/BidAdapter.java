package com.example.bidit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BidAdapter extends ArrayAdapter<Bid> {

	public BidAdapter(Context context, int resource) {
		super(context, resource);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.bids_list_item, null);
		}

		Bid it = this.getItem(position);
		TextView itemDescription = (TextView) v.findViewById(R.id.item_description);
		itemDescription.setText("this is where the description goes");
		TextView bidPrice = (TextView) v.findViewById(R.id.bid_price);
		bidPrice.setText("" + it.getPrice());

		
		return v;
	}

}