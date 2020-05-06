import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User, UnparsedActivity } from '../_models';
import { Activity } from '../_models';

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    getActivities() {
        return this.http.get<any>(`${config.apiUrl}/api/v1/activities`);
    }

    toggleActivity(activity:Activity, user:User) {
        return this.http.post<UnparsedActivity[]>(`${config.apiUrl}/api/v1/activity/toggle/${user.username}/${activity.id}`, {})
    }
}