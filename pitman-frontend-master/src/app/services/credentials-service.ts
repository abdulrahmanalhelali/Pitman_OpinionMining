import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SignUpUser } from '../models/signup-user';
import { LoginUser } from '../models/login-user';
import { EMPTY, from, of } from 'rxjs';
import { map, concatMap, finalize } from 'rxjs/operators';
import { Token } from '../models/token';

@Injectable({
    providedIn: 'root'
  })
export class CredentialsService{

    private url: string;
    
    constructor(private http: HttpClient){
        this.url = environment.baseUrl;
    }

    public signUp(signUpData: SignUpUser){
        return this.http.post(this.url+'/signup', signUpData);
    }

    public login(loginData: LoginUser){
        return this.http.post<Token>(this.url+'/login', loginData);
    }

    public facebookLogin() {
        // login with facebook and return observable with fb access token on success
        return from(new Promise<fb.StatusResponse>(resolve => FB.login(resolve)))
            .pipe(concatMap(({ authResponse }) => {
                if (!authResponse) return EMPTY;
                return of(authResponse.accessToken);
            }));
    }
}
