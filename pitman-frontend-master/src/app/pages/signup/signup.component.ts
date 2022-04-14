import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DialogComponent } from 'src/app/dialogs/dialog/dialog.component';
import { SignUpUser } from 'src/app/models/signup-user';
import { CredentialsService } from 'src/app/services/credentials-service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  private subscriptions: Subscription[] = [];
  private credentialsService: CredentialsService;

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(credentialsService: CredentialsService, public dialog: MatDialog, private router: Router) { 
    this.credentialsService = credentialsService;
  }

  ngOnInit() {
  }

  signup(username: string, email: string, password: string, passwordAgain: string) {
    if(password === passwordAgain && this.form.valid){
      let signUpData = new SignUpUser(username, email, password);
      this.subscriptions.push(this.credentialsService.signUp(signUpData).subscribe(res => {
      this.openDialog(
        {title: 'Succesful',
         content: 'You have registered successfully!'},
        ()=>{
          this.router.navigate(['/login'])
      });
      }, 
      err => {
        this.openDialog(
          {title: 'Error',
           content: (err.error.status + ' - ' + err.error.message),
          },
          undefined);
      }));
    }else{

    }

  }

  openDialog(data: any, afterClosed: any): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '30%',
      data: data,
    });

    dialogRef.afterClosed().subscribe(afterClosed);
  }

}
