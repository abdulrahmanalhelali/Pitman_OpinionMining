import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacebookLoginProvider, SocialAuthService, SocialUser } from 'angularx-social-login';
import { Subscription } from 'rxjs';
import { CredentialsService } from '../services/credentials-service';
import { QueueService } from '../services/queue-service';
import { UserService } from '../services/user-service';

@Component({
  selector: 'app-account-definitions',
  templateUrl: './account-definitions.component.html',
  styleUrls: ['./account-definitions.component.scss']
})
export class AccountDefinitionsComponent implements OnInit {

  private subscriptions: Subscription[] = [];
  socialUser: SocialUser | undefined;
  isLoggedin: boolean | undefined;
  profilePhotoLink: string | undefined;
  username: string | undefined;
  isTwitterNameEntered: boolean = false;
  twitterUser: string | undefined;

  constructor(
    private credentialsService: CredentialsService,
    private socialAuthService: SocialAuthService,
    private userService: UserService,
    private queueService: QueueService
    ) {

      this.socialAuthService.authState.subscribe((user) => {
        this.socialUser = user;
        this.isLoggedin = (user != null);
        console.log(this.isLoggedin)
        if(this.isLoggedin){
          this.subscriptions.push(this.userService.setFbToken(user.authToken).subscribe(res=>{
            console.log(res)
          },
          err => {
            console.log(err)
          }))
          
        }
        console.log(user)
        this.profilePhotoLink = user.photoUrl;
        this.username = user.firstName+" "+user.lastName;
      });
  }

  ngOnInit(): void {
  }

  facebookLogin() {
    /*this.subscriptions.push(this.credentialsService.facebookLogin().subscribe(res => {
      console.log(res)
    }))*/
    const fbLoginOptions = {
      scope: 'pages_messaging,pages_messaging_subscriptions,email,pages_show_list,user_posts'
    };
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID, fbLoginOptions);

  }

  twitterUsername(input: string){
    
    this.subscriptions.push(this.userService.setTwToken(input).subscribe(res => {
      this.isTwitterNameEntered = true;
      this.twitterUser = input;
    },
    err => {
      console.log(err)
    }
    ))
  }

  addToQueue(platform: string){
    this.subscriptions.push(this.queueService.addToQueue(platform).subscribe(res => {
      console.log(res)
    },
    err => {
      console.log(err)
    }))
  }

}
