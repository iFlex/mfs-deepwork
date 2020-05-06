import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { User, UnparsedActivity } from '../_models';
import { Activity } from '../_models';
import { UserService } from '../_services';
import { st } from '@angular/core/src/render3';

@Component({templateUrl: 'home.component.html',styleUrls: ['./home.component.css']})
export class HomeComponent implements OnInit {
    currentUser: User;
    users: User[] = [];
    activities: Activity[];
   
    constructor(private userService: UserService) {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
        this.activities = [];
        console.log("We have a local user, bawss");
        console.log(this.currentUser)

        setInterval(() => {this.recomputeDurations()}, 1000);
    }

    ngOnInit() {
        this.userService.getActivities().subscribe((data) => {
            console.log(data);
            for(let i in data){
                this.updateOrInsertActivity(data[i])
            }
        })
    }

    getActivityById(id:string){
        for(let i in this.activities){
            if(this.activities[i].id == id){
                return this.activities[i]
            }
        }

        return null;
    }

    updateOrInsertActivity(activity:UnparsedActivity){
        if(!activity){
            alert("Server responded wity null")
            return;
        }
        
        for(let i in this.activities){
            if(this.activities[i].id == activity.id){
                this.activities[i] = Activity.fromUnparsed(activity);
                return;
            }
        }

        this.activities.push(Activity.fromUnparsed(activity));
    }

    toggleActivity(event: any) {
        let id = event.target.id;
        let activity = this.getActivityById(id)
        if(activity == null){
            alert("Invalid Activity ID:"+activity);
            return;
        }
        this.userService.toggleActivity(activity, this.currentUser).subscribe((data) => {
            for(let i in data){
                this.updateOrInsertActivity(data[i]);
                activity.recomputeStats();
            }
        })
    }

    recomputeDurations() {
        for(let i in this.activities){
            this.activities[i].recomputeStats();
            
            this.activities[i].status = "(";
            if(!this.activities[i].isInProgress()){
                this.activities[i].status += "duration:";
            }
            this.activities[i].status += this.prettyPrintDuration(this.activities[i].timeInProgress);
            this.activities[i].status += ")";
        }
    }

    prettyPrintDuration(duration:number){
        let dividers = [1000*60*60,1000*60,1000]
        let name     = ["h","m","s"]
        let result   = [];
        
        let i = 0;
        while(duration > 0 && i < dividers.length){
            let cat  = Math.floor(duration / dividers[i]);
            let rest = Math.floor(duration % dividers[i]);
            
            result[i] = cat;
            duration = rest;
            ++i;
        }

        let stresult = "";
        for(let i = 0; i < result.length; ++i){
            if( result[i] > 0 ) {
                stresult += result[i] + name[i];
            }
        }
        return stresult;
    }
}