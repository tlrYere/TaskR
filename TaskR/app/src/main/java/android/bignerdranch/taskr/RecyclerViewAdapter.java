package android.bignerdranch.taskr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Calendar;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<UUID> mIds = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private Context mContext;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    public RecyclerViewAdapter(Context context, ArrayList<UUID> ids, ArrayList<String> titles, ArrayList<String> dates) {
        mIds = ids;
        mTitles = titles;
        mDates = dates;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tasks_title.setText(mTitles.get(position));
        holder.tasks_date.setText(mDates.get(position));

        if (MainActivity.getTask(mIds.get(position)).isCompleted()) {
            //holder.tasks_checkbox.setVisibility(View.INVISIBLE);
            holder.tasks_checkbox.setChecked(true);
            //holder.tasks_checkbox.setEnabled(false);
            holder.tasks_checkbox.setClickable(false);
        }

        //TODO: Kyle, right here is where upon checking the box it does stuff
        holder.tasks_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Task currentTask = MainActivity.getTask(mIds.get(position));
                    currentTask.setCompleted(true);
                    MainActivity.updateTask(mIds.get(position), currentTask);
                    mContext.startActivity(new Intent(mContext, MainActivity.class)); // TODO: Needs to be fixed maybe
                }
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO: TO BE REMOVED - Just a test
                Toast.makeText(mContext, mTitles.get(position), Toast.LENGTH_SHORT).show();

                // Dialog - Task Viewing
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View mView = inflater.inflate(R.layout.dialog_task, null);

                final String taskViewTitle = mTitles.get(position);
                String taskViewDateString = mDates.get(position);
                String[] taskViewTokens = taskViewDateString.split(" at ");   //separates the date and time from each other
                String taskViewDate = taskViewTokens[0];
                final String taskViewTime = taskViewTokens[1];
                final String taskViewDescription = MainActivity.getTask(mIds.get(position)).getmDescription();

                final EditText mTitle = (EditText) mView.findViewById(R.id.dialogTitle);
                final TextView mDate = (TextView) mView.findViewById(R.id.dialogDate);
                final TextView mTime = (TextView) mView.findViewById(R.id.dialogTime);
                final EditText mDescription = (EditText) mView.findViewById(R.id.dialogDescription);
                final CheckBox mCheckBox = (CheckBox) mView.findViewById(R.id.isCompleted);

                mTitle.setText(taskViewTitle);
                mDate.setText(taskViewDate);
                mTime.setText(taskViewTime);
                mDescription.setText(taskViewDescription);

                Button mEdit = (Button) mView.findViewById(R.id.editButton);
                Button mDelete = (Button) mView.findViewById(R.id.deleteButton);

                final Spinner spinnerDifficulty = mView.findViewById(R.id.spinnerDifficultView);
                ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(mContext, R.array.difficulty, android.R.layout.simple_spinner_item);
                adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDifficulty.setAdapter(adapterDifficulty);
                //spinner.setOnItemSelectedListener(this); //TODO: Needs to be finished for difficulty
                int spinnerPosition = adapterDifficulty.getPosition(MainActivity.getTask(mIds.get(position)).getDifficulty());
                spinnerDifficulty.setSelection(spinnerPosition);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dateDialog = new DatePickerDialog(mContext, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                        dateDialog.show();

                    }
                });

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" + dayOfMonth + "/" + year;
                        mDate.setText(date);

                    }
                };

                mTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR);
                        int minutes = cal.get(Calendar.MINUTE);
                        boolean isTwentyFour = false;

                        TimePickerDialog timeDialog = new TimePickerDialog(mContext, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mTimeSetListener, hour, minutes, isTwentyFour);
                        timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                        timeDialog.show();

                    }
                });

                mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String time = hourOfDay + ":" + minute;
                        //mTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        int hour = hourOfDay % 12;
                        if (hour == 0)
                            hour = 12;
                        mTime.setText(String.format("%02d:%02d %s", hour, minute,
                                hourOfDay < 12 ? "AM" : "PM"));
                    }
                };

                mEdit.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        if (!spinnerDifficulty.getSelectedItem().toString().equals("Difficulty")) {
                            Task updatedTask = new Task(mTitle.getText().toString(), mDescription.getText().toString(),
                                    mDate.getText().toString() + " at " + mTime.getText().toString(),
                                    spinnerDifficulty.getSelectedItem().toString());
                            MainActivity.updateTask(mIds.get(position), updatedTask);

                            dialog.dismiss();
                            mContext.startActivity(new Intent(mContext, MainActivity.class)); //TODO: Needs to be fixed maybe
                        }
                    }
                });

                    mDelete.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {

                        final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(mContext);
                        LayoutInflater inflater2 = LayoutInflater.from(mContext);
                        View mView2 = inflater2.inflate(R.layout.dialog_warning, null);

                        Button mNo = (Button) mView2.findViewById(R.id.no);
                        Button mYes = (Button) mView2.findViewById(R.id.yes);

                        mBuilder2.setView(mView2);
                        final AlertDialog dialog2 = mBuilder2.create();
                        dialog2.show();

                        mNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });

                        mYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainActivity.deleteTask(mIds.get(position));
                                dialog2.dismiss();
                                mContext.startActivity(new Intent(mContext, MainActivity.class)); //TODO: Needs to be fixed maybe
                                //((Activity) mContext).recreate();
                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox tasks_checkbox;
        TextView tasks_title;
        TextView tasks_date;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tasks_title = itemView.findViewById(R.id.task_title);
            tasks_date = itemView.findViewById(R.id.task_date);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            tasks_checkbox = itemView.findViewById(R.id.isCompleted);

        }
    }

}