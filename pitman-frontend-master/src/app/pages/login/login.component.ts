import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { CredentialsService } from 'src/app/services/credentials-service';
import { MatDialog } from '@angular/material/dialog';
import { LoginUser } from 'src/app/models/login-user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private subscriptions: Subscription[] = [];
  private credentialsService: CredentialsService;

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(credentialsService: CredentialsService, public dialog: MatDialog, private router: Router) {
    this.credentialsService = credentialsService;
  }

  ngOnInit() {
  }

  login(username: string, password: string) {

    if (this.form.valid) {
      let loginData = new LoginUser(username, password);
      this.subscriptions.push(this.credentialsService.login(loginData).subscribe(res => {
        localStorage.setItem('token', res.token)
        this.router.navigate([''])
      }, 
      err => {
        console.log(err)
      }))
    }
  }

}
