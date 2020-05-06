import { runInThisContext } from "vm";
import { UnparsedActivity } from "./unparsed-activity"

export class Activity {
    id: string;
    name: string;
    tags: string[];
    started: Date; 
    stopped: Date;
    status: string;

    //durations
    timeSinceStart: number;
    timeSinceStop: number;
    timeInProgress: number;

    public static fromUnparsed(ua:UnparsedActivity){
        let result:Activity = new Activity();
        result.id = ua.id;
        result.name = ua.name;
        result.tags = ua.tags;
        if(ua.started)
            result.started = new Date(Date.parse(ua.started));
        if(ua.stopped)
            result.stopped = new Date(Date.parse(ua.stopped));

        return result;
    }

    static timeDelta(start: Date, stop: Date) {
        return stop.getTime() - start.getTime();
    }

    static timeSince(a:Date) {
        return Date.now() - a.getTime();
    }

    static timeTo(a:Date){
        return a.getTime() - Date.now();
    }

    isInProgress() {
        return this.started && (this.stopped == null || this.stopped == undefined)
    }

    recomputeStats(){
        this.timeSinceStart = -1;
        this.timeInProgress = -1;
        this.timeSinceStop  = -1;

        if(this.started) {
            this.timeSinceStart = Activity.timeSince(this.started);
            this.timeInProgress = Activity.timeSince(this.started);
        }
        if(this.stopped) {
            this.timeInProgress = Activity.timeDelta(this.started, this.stopped);
            this.timeSinceStart = Activity.timeSince(this.stopped);
        }
    }
}