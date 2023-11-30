import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ChangePasswordComponent } from 'src/app/material-component/dialog/change-password/change-password.component';
import { ConfirmationComponent } from 'src/app/material-component/dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class AppHeaderComponent {
role:any;

  constructor(private router :Router,
    private dialog:MatDialog) {
  }

  logout() {
  const dialogConfig = new MatDialogConfig();
  dialogConfig.data = {
    message: 'Logout',
    confirmation: true
  };

  // Open a dialog with ConfirmationComponent
  const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

  // Subscribe to the onEmitStatusChange event from the ConfirmationComponent
  const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((response) => {
    // When the user confirms, navigate to the home page and then close the dialog
    this.router.navigate(['/']);
    dialogRef.close();
  });
}

    changePassword(){
      const dialogConfig=new MatDialogConfig();
      dialogConfig.width="550px";
      this.dialog.open(ChangePasswordComponent,dialogConfig);
      
    }
  }

