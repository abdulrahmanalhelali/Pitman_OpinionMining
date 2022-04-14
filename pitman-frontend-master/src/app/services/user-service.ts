import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Token } from "../models/token";

@Injectable({
    providedIn: 'root'
})
export class UserService{

    private url: string;
    
    constructor(private http: HttpClient){
        this.url = environment.baseUrl + '/user';
    }

    public setFbToken(fbToken: string){
        console.log(fbToken)
        let token = new Token(fbToken);
        let options = {headers: new HttpHeaders({'Authorization': localStorage.getItem('token')!})};
        return this.http.put(this.url+'/fb', token, options)
    }

    public setTwToken(twToken: string){
        console.log(twToken)
        let token = new Token(twToken);
        let options = {headers: new HttpHeaders({'Authorization': localStorage.getItem('token')!})};
        return this.http.put(this.url+'/tw', token, options)
    }
}