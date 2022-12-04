package com.android.attendance.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;
import java.util.ArrayList;
import java.util.Objects;

public class ViewStudentByBranchYear extends Activity {

	ArrayList<StudentBean> studentBeanList;
	private ArrayAdapter<String> listAdapter;
	String branch;
	String year;
	//String semester;
	

	DBAdapter dbAdapter = new DBAdapter(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.__listview_main);

		ListView listView = (ListView) findViewById(R.id.listview);
		final ArrayList<String> studentList = new ArrayList<>();
		
		 branch= Objects.requireNonNull(getIntent().getExtras()).getString("branch");
		 year =getIntent().getExtras().getString("year");


		studentBeanList=dbAdapter.getAllStudentByBranchYear(branch, year );

		for(StudentBean studentBean : studentBeanList)
		{
			String users = studentBean.getStudent_firstname()+","+studentBean.getStudent_lastname()+","+studentBean.getStudent_semester();
					
			studentList.add(users);
			Log.d("users: ", users); 

		}

		listAdapter = new ArrayAdapter<>(this, R.layout.view_student_list, R.id.label, studentList);
		listView.setAdapter( listAdapter ); 

		listView.setOnItemLongClickListener((arg0, arg1, position, arg3) -> {



			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewStudentByBranchYear.this);

			alertDialogBuilder.setTitle(getTitle()+" Decision!");
			alertDialogBuilder.setMessage("Are you sure want to delete?");

			alertDialogBuilder.setPositiveButton("Yes", (dialog, id) -> {

				studentList.remove(position);
				listAdapter.notifyDataSetChanged();
				listAdapter.notifyDataSetInvalidated();

				dbAdapter.deleteStudent(studentBeanList.get(position).getStudent_id());
				studentBeanList=dbAdapter.getAllStudentByBranchYear(branch, year );

				for(StudentBean studentBean : studentBeanList)
				{
					String users = " FirstName: " + studentBean.getStudent_firstname()+"\nLastname:"+studentBean.getStudent_lastname() ;
					studentList.add(users);
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
