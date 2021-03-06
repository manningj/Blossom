package bruteforce.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bruteforce.blossom.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bruteforce.application.Services;
import bruteforce.business.AccessTask;
import bruteforce.objects.Account;
import bruteforce.objects.Task;

/**
 Class: MainActivity
 Author: Triet Nguyen
 Purpose: To set up front-end stuff for main page
 */
public class MainActivity extends AppCompatActivity {
    //fields
    private Account accounts;
    private String userNameLogIn;
    private AccessTask tasks;
    private Task testTask;

    /**
     onCreate

     Purpose: setup everything for main page
     Parameters: Bundle savedInstanceState
     Returns: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set this MainActivity to work activity_main.xml


        userNameLogIn = Services.getAccount().getUsername();

        tasks = new AccessTask(userNameLogIn);
        final List<Task> doneTask = tasks.getTaskList();
        //create new AccessTask with "username1" and get list of tasks of username1

        //Change thing here for testing
        final List<Task> taskDetails = new ArrayList<>();
        for (int i = 0; i < doneTask.size(); i++) {
            if (!doneTask.get(i).getCompleted()) {
                taskDetails.add(doneTask.get(i));
            }
        }

        final ArrayAdapter<Task> taskArrayAdapter = new ArrayAdapter<Task>(
                this, android.R.layout.simple_list_item_2, android.R.id.text1, taskDetails) {
            //create ArrayAdapter to implement sub-item in ListView

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);

                TextView text1 = view.findViewById(android.R.id.text1);
                //create TextView for text1 in simple_list_item
                TextView text2 = view.findViewById(android.R.id.text2);
                //create TextView for text2 in simple_list_item

                String checkCompletion = getCompletionString(taskDetails.get(position).getCompleted());

                String checkPriority = getPriorityString(taskDetails.get(position).getPriority());

                String text1Str = String.format("%s - %s - %s", taskDetails.get(position).getName(), checkPriority, checkCompletion);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.CANADA);
                String text2Str = dateFormat.format(taskDetails.get(position).getDeadline());

                //Make string format for text1 and text2

                text1.setText(text1Str);
                text2.setText(text2Str);
                //Set two strings to text1 and text2

                return view;

            }
        };

        ListView listView = findViewById(R.id.list1);
        listView.setAdapter(taskArrayAdapter);
        //Implement above adapter into ListView in activity_main.xml

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //check which item in ListView is clicked

                Intent intent = new Intent(MainActivity.this, ShowTaskActivity.class);
                Task test = taskArrayAdapter.getItem(position);
                intent.putExtra("key", test);
                //intent.putExtra("user",userNameLogIn);
                MainActivity.this.startActivity(intent);
                //Move to ShowTaskActivity to get more task options
            }
        });

        final Button addToTask = (Button) findViewById(R.id.button);
        //create Button object to handle Add new task button in activity_main.xml

        addToTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //these codes will be executed when Add new task button is clicked

                try {
                    //String userName = "username1";
                    Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                    //intent.putExtra("key",userNameLogIn);
                    //pass username to AddTaskActivity

                    MainActivity.this.startActivity(intent);
                    //start AddTaskActivity

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //create Button object for Add new task button in activit_main.xml, it will
        //has a function to move from Main page to Add Task page. Intent object is used to transform
        //from one page to another page

        final Button completeTaskList = (Button) findViewById(R.id.button4) ;

        completeTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //String userName = "username1";
                    Intent intent = new Intent(MainActivity.this, ShowCompletedTaskActivity.class);
                    //intent.putExtra("key", userNameLogIn);
                    MainActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     onCreateOptionsMenu

     Purpose: setup default menu
     Parameters: Menu menu
     Returns: boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
    onOpTionsItemSelected

     Purpose: setup settings button on toolbar
     Parameters: MenuItem item
     Returns: boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent transit = new Intent(MainActivity.this, AccountInfoActivity.class);
                startActivity(transit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    /**
     onBackPressed

     Purpose: setup soft back button for Main page
     Parameters: none
     Returns: none
     */
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    /**
     backButtonHandler

     Purpose: show dialog for closing application
     Parameters: none
     Returns: none
     */
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //Setting Dialog Title
        alertDialog.setTitle("Leave application?");
        //Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");
        //Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        //Setting Negative "No" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public void plantOnClick(View v) {
        Intent plant = new Intent(MainActivity.this,PlantActivity.class);
        startActivity(plant);
    }

    /**
     getCompletionString

     Purpose: return a string when user check the box
     Parameters: boolean done
     Returns: String
     */
    public String getCompletionString(boolean done) {
        String checkCompletion = "";
        if (done) {
            checkCompletion = "Done";
        } else {
            checkCompletion = "Ongoing";
        }
        return checkCompletion;
    }

    /**
     getPriorityString

     Purpose: return a string when user choose priority
     Parameters: int num
     Returns: String
     */
    public void summaryOnClick(View v){
        Intent summary = new Intent(MainActivity.this,SummaryActivity.class);
        startActivity(summary);
    }
    public String getPriorityString(int num) {
        String checkPriority = "";
        if (num == 0) {
            //if priority in Task object is 0, return Low
            checkPriority = "Low";
        } else if (num == 1) {
            //if priority in Task object is 1, return Medium
            checkPriority = "Medium";
        } else {
            //if priority in Task object is 2, return High
            checkPriority = "High";
        }
        return checkPriority;
    }

}
