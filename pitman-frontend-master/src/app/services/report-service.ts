import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Token } from "../models/token";

@Injectable({
    providedIn: 'root'
})
export class ReportService{
    private url: string;
    
    constructor(private http: HttpClient){
        this.url = environment.baseUrl + '/topics';
    }

    public getReport():any{
        let options = {headers: new HttpHeaders({'Authorization': localStorage.getItem('token')!})};
        return this.http.get(this.url, options)
    }
}