package cn.edu.ncwu.www.preferences;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.Preference;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.edu.ncwu.www.R;

public class ButtonPreferences extends Preference {
	private static final String TAG = "IconPreferenceScreen";
	private Drawable mIcon;
	private Context context;
	View.OnClickListener clickListener = null;
	LinearLayout linearLayout;
	private String action = null;
	private String uri = null;
	private String category = null;
	private String connection = null;
	private String qqQun ;
	private String toastText;
	TypedArray a = null;

	public ButtonPreferences(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

		// TODO Auto-generated constructor stub

	}

	public ButtonPreferences(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		setLayoutResource(R.layout.preference_icon);
		a = context.obtainStyledAttributes(attrs,
				R.styleable.IconPreferenceScreen, defStyle, 0);
		mIcon = a.getDrawable(R.styleable.IconPreferenceScreen_icon);
		action = a.getString(R.styleable.IconPreferenceScreen_action);
		uri = a.getString(R.styleable.IconPreferenceScreen_uriconnection);
		connection = a.getString(R.styleable.IconPreferenceScreen_connection);
		category = a.getString(R.styleable.IconPreferenceScreen_category);
		toastText = a.getString(R.styleable.IconPreferenceScreen_toasttext);
		qqQun = a.getString(R.styleable.IconPreferenceScreen_QQqun);

	}

	@Override
	public void onBindView(View view) {
		super.onBindView(view);
		Log.i(TAG, "[onBindView](0)");
		linearLayout = (LinearLayout) view.findViewById(R.id.ll);
		ImageView imageView = (ImageView) view.findViewById(R.id.icon);

		if (imageView != null && mIcon != null) {
			imageView.setImageDrawable(mIcon);
			Log.i(TAG, "[onBindView](1)");

		}
		Log.i(TAG, "[onBindView](2)");
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		super.onClick();
		Intent intent = new Intent();

		if ((!uri.equals("")) && (!action.equals("")) && (!category.equals(""))) {
			intent.setAction(action);
			intent.addCategory(category);
			intent.setData(Uri.parse(uri));
			if (intent.resolveActivity(context.getPackageManager()) != null)
				context.startActivity(intent);
			else if ((!connection.equals("")) && (!action.equals("")))

			{
				intent.setAction(action);
				intent.setData(Uri.parse(connection));
				context.startActivity(intent);
				if (!toastText.equals(""))
					Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
			}

		} 

		if (this.getKey().equals("qq")) {
		    
		    Intent qqintent = new Intent();

	        if ((!uri.equals("")) && (!action.equals("")) && (!category.equals(""))) {
	            qqintent.setAction(action);
	            qqintent.addCategory(category);
	            qqintent.setData(Uri.parse(uri));
	            if (intent.resolveActivity(context.getPackageManager()) != null)
	                context.startActivity(qqintent);
	         

	        } 
			if (!toastText.equals("")) {
				ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setText(qqQun);

				Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
			}
		}
	}
}
