package android.bignerdranch.taskr;

import java.util.UUID;

public class Task{

    private UUID mId;
    private String mName;
    private String mDescription;
    private String mDateAndTimeDue;
    private boolean mCompleted;

    public Task(String taskName, String taskDescription, String taskDateAndTimeDue)
    {
        mId = UUID.randomUUID();
        mName = taskName;
        mDescription = taskDescription;
        mDateAndTimeDue = taskDateAndTimeDue;
        mCompleted = false;     //starts off not completed
    }

    public Task(UUID id)
    {
        mId = id;
    }

    public UUID getId() { return mId; }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDateAndTimeDue() {
        return mDateAndTimeDue;
    }

    public void setmDateAndTimeDue(String mDateAndTimeDue) {
        this.mDateAndTimeDue = mDateAndTimeDue;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
