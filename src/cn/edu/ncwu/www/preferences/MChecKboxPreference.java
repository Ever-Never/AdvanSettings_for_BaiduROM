package cn.edu.ncwu.www.preferences;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import cn.edu.ncwu.www.R;

public class MChecKboxPreference extends CheckBoxPreference {

	private Context mContext;
	private String resloverKey;
	private ContentResolver resolver;

	public MChecKboxPreference(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

		// TODO Auto-generated constructor stub

	}

	public MChecKboxPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.setLayoutResource(R.layout.icon_checkbox_preference);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MCheckBoxPreference, defStyle, 0);
		resloverKey = ta.getString(R.styleable.MCheckBoxPreference_resloverkey);

		resolver = context.getContentResolver();


	}
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		 if (this.isChecked()) {
				android.provider.Settings.System.putInt(resolver,
						resloverKey, 0);
			} else {
				android.provider.Settings.System.putInt(resolver,
						resloverKey, 1);
			}
		 Log.d("clik", "text") ;
		 
		super.onClick();
	}

 


}
