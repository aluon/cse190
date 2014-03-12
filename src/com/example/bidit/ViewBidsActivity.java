package com.example.bidit;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ViewBidsActivity extends BiditActivity {
	
	BidAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_bids);
		
		adapter = new BidAdapter(this, R.layout.bids_list_item);
		ListView lv = (ListView)findViewById(R.id.bid_list);
		lv.setAdapter(adapter);
		new RequestBidsTask().execute();
	}
	
	public class RequestBidsTask extends AsyncTask<Void, Bid, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			HttpGet request = new HttpGet(Util.BID_API);
			try {
				HttpResponse response = Util.getHttpClient()
						.execute(request);
				String content = EntityUtils.toString(response.getEntity());
				JSONObject json = new JSONObject(content);
				JSONArray objects = json.getJSONArray("objects");
				for (int i = 0; i < 3; ++i) {
					JSONObject o = objects.getJSONObject(i);
					User seller = null;
					User buyer = null;
					Ad ad = null;
					BigDecimal price = new BigDecimal(o.getDouble("price"));
					
					Bid bid = new Bid(price, buyer, seller, ad);
					publishProgress(bid);
				}
				Log.d(ViewBidsActivity.class.getName(), content);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Bid... bids) {
			adapter.addAll(bids);
			adapter.notifyDataSetChanged();
			Log.d(ViewBidsActivity.class.getName(), "count: " + adapter.getCount());
		}
	}

	@Override
	public void onLoginSuccessful() {
		// TODO Auto-generated method stub

	}

}