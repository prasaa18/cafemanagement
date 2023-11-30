import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {


  uri=environment.apiUrl;

  constructor(private httpClient:HttpClient) { }
  

  getDetais(){

   return this.httpClient.get(this.uri+"/dashboard/details");
   
  }
}
