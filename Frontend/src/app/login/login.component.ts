import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  hide =true;
  password =true;
  loginForm:any=FormGroup;
  responceMessage:any;

  constructor(private formBuilder:FormBuilder,
    private router: Router,
    private userService:UserService,
    public dialogref:MatDialogRef<LoginComponent>,
    private ngxService:NgxUiLoaderService,
    private snackBarService:SnackbarService) { }

  ngOnInit(): void {
    this.loginForm=this.formBuilder.group({
      email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]],
      password:[null,[Validators.required]]
    })
  }
 
  togglePasswordVisibility() {
    this.password = !this.password;

  }
  handleSubmit(){
    this.ngxService.start();
    var formData=this.loginForm.value;
    var data={
      email:formData.email,
      password:formData.password
    }
    this.userService.login(data).subscribe((response:any)=>{
      this.ngxService.stop();
      this.responceMessage=response ?.message;
      this.dialogref.close();
      localStorage.setItem('token',response.token);
      this.router.navigate(['/cafe/dashboard'])



    },(error)=>{
      this.ngxService.stop();
      if(error.error?.message){
        this.responceMessage=error.error?.message;
      }
      else{
        this.responceMessage=GlobalConstants.genericError;
      }
    this.snackBarService.openSnackbar(this.responceMessage,GlobalConstants.error)
    })
  
    
  }

}
