import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

export type HealthStatus = 'UP' | 'DOWN' | 'UNKNOWN' | 'OUT_OF_SERVICE';

export type HealthKey = 'binders' | 'diskSpace' | 'mail' | 'elasticsearch' | 'mongo';

export interface Health {
  status: HealthStatus;
  details: {
    [key in HealthKey]?: HealthDetails;
  };
}

export interface HealthDetails {
  status: HealthStatus;
  details: any;
}

@Injectable({ providedIn: 'root' })
export class HealthService {
  constructor(private http: HttpClient) {}

  checkHealth(): Observable<Health> {
    return this.http.get<Health>(SERVER_API_URL + 'management/health');
  }
}
