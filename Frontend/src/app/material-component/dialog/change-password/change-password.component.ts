import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { error } from 'console';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  oldPassword=true;
  newPassword=true;
  confirmpassword=true;
  changePasswordForm:any=FormGroup;
  responceMessage:any;
  
  
  constructor(private formBuilder :FormBuilder,
    private userService:UserService,
    public dialogRef:MatDialogRef<ChangePasswordComponent>,
    private ngxService:NgxUiLoaderService,
    private snackBarService:SnackbarService) { }

  ngOnInit(): void {

    this.changePasswordForm=this.formBuilder.group({
      oldPassword:[null,Validators.required],
      newPassword:[null,Validators.required],
      confirmpassword:[null,Validators.required],
    })
    
  }

  toggleOldPasswordVisibility() {
    this.oldPassword = !this.oldPassword;

  }
  toggleNewPasswordVisibility(){
   this.newPassword=!this.newPassword;
  }
  toggleConfirmPasswordVisibility(){
    this.confirmpassword=!this.confirmpassword;
  }

  validateSubmit()
  {
    
    if(this.changePasswordForm.controls['newPassword'].value!=this.changePasswordForm.controls['confirmpassword'].value)
    return true;
  else{
    return false;
  }
  }

  handleSubmit(){
    this.ngxService.start();
    var formData=this.changePasswordForm.value;
    var Data={
      oldPassword:formData.oldPassword,
      newPassword:formData.newPassword,
      confirmpassword:formData.confirmpassword

    }
  this.userService.chngePassword(Data).subscribe((response:any)=>{
    this.ngxService.stop();
    this.responceMessage=response.message;
    this.dialogRef.close();
    this.snackBarService.openSnackbar(this.responceMessage,"Success")
  },(error)=>{
          console.log(error);
          this.ngxService.stop();
          if(error.error?.message){
            this.responceMessage=error.error?.message;
          }
          else{
            this.responceMessage=GlobalConstants.genericError;

          }
          this.snackBarService.openSnackbar(this.responceMessage,GlobalConstants.error);
  })
  

}


}
