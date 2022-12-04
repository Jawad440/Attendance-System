package com.android.attendance.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.attendance.bean.FacultyBean;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class ViewFacultyActivity extends Activity {

	ArrayList<FacultyBean> facultyBeanList;
	private ArrayAdapter<String> listAdapter;

	DBAdapter dbAdapter = new DBAdapter(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.__listview_main);

		ListView listView = (ListView) findViewById(R.id.listview);
		final ArrayList<String> facultyList = new ArrayList<>();

		facultyBeanList=dbAdapter.getAllFaculty();

		for(FacultyBean facultyBean : facultyBeanList)
		{
			String users = " FirstName: " + facultyBean.getFaculty_firstname()+"\nLastname:"+facultyBean.getFaculty_lastname();
				
			facultyList.add(users);
			Log.d("users: ", users); 

		}

		listAdapter = new ArrayAdapter<>(this, R.layout.view_faculty_list, R.id.labelF, facultyList);
		listView.setAdapter( listAdapter ); 

		listView.setOnItemLongClickListener((arg0, arg1, position, arg3) -> {



			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewFacultyActivity.this);

			alertDialogBuilder.setTitle(getTitle()+" Decision!");
			alertDialogBuilder.setMessage("Are you sure want to delete?");

			alertDialogBuilder.setPositiveButton("Yes", (dialog, id) -> {

				facultyList.remove(position);
				listAdapter.notifyDataSetChanged();
				listAdapter.notifyDataSetInvalidated();

				dbAdapter.deleteFaculty(facultyBeanList.get(position).getFaculty_id());
				facultyBeanList=dbAdapter.getAllFaculty();

				for(FacultyBean facultyBean : facultyBeanList)
				{
					String users = " FirstName: " + facultyBean.getFaculty_firstname()+"\nLastname:"+facultyBean.getFaculty_lastname();
					facultyList.add(users);
					Log.d("users: ", users);

				}

			});
			alertDialogBuilder.setNegativeButton("No", (dialog, id) -> {
				// cancel the alert box and put a Toast to the user
				dialog.cancel();
				Toast.makeText(getApplicationContext(), "You choose cancel",
						Toast.LENGTH_LONG).show();
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			// show alert
			alertDialog.show();





			return false;
		});




	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
