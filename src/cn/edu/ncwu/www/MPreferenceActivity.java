package cn.edu.ncwu.www;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import cn.edu.ncwu.www.tools.RootCmd;

public class MPreferenceActivity extends PreferenceActivity {

	private PreferenceScreen recovery;
	private PreferenceScreen replaceRecovery;
	private static final int OPEN_FILE = 0;
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		addPreferencesFromResource(R.xml.preferences);
		recovery = (PreferenceScreen) findPreference("recovery");
		replaceRecovery = (PreferenceScreen) findPreference("replaceRecovery");
		
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return true;

	}



	@Override
	@Deprecated
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		if (preference == recovery) {
			ab.setTitle("重启到recovery").setMessage("是否重启到recovery")
					.setNegativeButton("否", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							arg0.dismiss();
						}
					}).setPositiveButton("是", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							RootCmd.RunRootCmd("reboot recovery");

						}
					}).show();
		} else if (preference == replaceRecovery) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			try {
				startActivityForResult(
						Intent.createChooser(intent, "请选择recovery.img"),
						OPEN_FILE);
			} catch (android.content.ActivityNotFoundException ex) {

			}

		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case OPEN_FILE:
			if (resultCode == RESULT_OK) {
				if (data.getData() != null) {
					file = new File(data.getData().getPath());
					if (file.getName().endsWith(".img"))
						RootCmd.RunRootCmd("dd if=" + file.getAbsolutePath()
								+ "/" + file.getName() + " of=/dev/recovery");
					else
						Toast.makeText(this, "请选择镜像文件，（.img结尾）",
								Toast.LENGTH_LONG).show();
				}

			}

			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
