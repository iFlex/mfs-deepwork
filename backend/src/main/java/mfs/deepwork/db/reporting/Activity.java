package mfs.deepwork.db.reporting;

import java.time.Instant;
import java.util.Date;

public class Activity {
    private String name;
    private String[] tags;
    private String id;
    private Date started;
    private Date stopped;

    public Activity(){

    }

    public Activity(Activity base){
        this.name = base.getName();
        this.id = base.getId();

        String[] tags = base.getTags();
        if(tags != null){
            this.tags = new String[tags.length];
            for(int i = 0 ; i < base.getTags().length; ++i){
                this.tags[i] = tags[i];
            }
        }
    }

    public void reset(){
        started = null;
        stopped = null;
    }

    public boolean isNotStarted(){
        return started == stopped && started == null;
    }

    public boolean isInProgress() {
        return started != null && stopped == null;
    }

    public boolean isFinished() {
        return started != null && stopped != null;
    }

    public void toggle(){
        if(isNotStarted() || isFinished()){
            reset();
            started = Date.from(Instant.now());
        } else if(isInProgress()) {
            stopped = Date.from(Instant.now());
        } else {
            //invalid state: started == null && ended != null
            reset();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getStopped() {
        return stopped;
    }

    public void setStopped(Date stopped) {
        this.stopped = stopped;
    }

    public long getDuration(){
        if(isFinished()){
            return stopped.getTime() - started.getTime();
        }
        return 0;
    }

    public Activity clone(){
        return new Activity(this);
    }
}
