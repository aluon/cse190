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

public class ViewMessagesActivity extends BiditActivity {
	
MessageAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_messages);
		
		adapter = new MessageAdapter(this, R.layout.messages_list_item);
		ListView lv = (ListView)findViewById(R.id.message_list);
		lv.setAdapter(adapter);
		new RequestMessagesTask().execute();
	}
	
	public class RequestMessagesTask extends AsyncTask<Void, Message, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			HttpGet request = new HttpGet(Util.MESSAGE_API);
			try {
				HttpResponse response = Util.getHttpClient()
						.execute(request);
				String content = EntityUtils.toString(response.getEntity());
				JSONObject json = new JSONObject(content);
				JSONArray objects = json.getJSONArray("objects");
				for (int i = 0; i < 3; ++i) {
					JSONObject o = objects.getJSONObject(i);
					User sender = null;
					User receiver = null;
					String msg_content = o.getString("content");
					
					Message message = new Message(sender, receiver, msg_content);
					publishProgress(message);
				}
				Log.d(ViewMessagesActivity.class.getName(), content);
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
		protected void onProgressUpdate(Message... messages) {
			adapter.addAll(messages);
			adapter.notifyDataSetChanged();
			Log.d(ViewMessagesActivity.class.getName(), "count: " + adapter.getCount());
		}
	}

	@Override
	public void onLoginSuccessful() {
		// TODO Auto-generated method stub

	}

}