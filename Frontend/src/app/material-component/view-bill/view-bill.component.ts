import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { BillService } from 'src/app/services/bill.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ViewBillProductsComponent } from '../dialog/view-bill-products/view-bill-products.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-view-bill',
  templateUrl: './view-bill.component.html',
  styleUrls: ['./view-bill.component.scss']
})
export class ViewBillComponent implements OnInit {

  displayedColumns: string[] = ['name', 'email', 'contactNumber', 'paymentMethod', 'total', 'view'];
  dataSource: any;
  responseMessage: any;

  constructor(private billservice: BillService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router) { }

  ngOnInit(): void {

    this.ngxService.start();
    this.tableData();


  }
  tableData() {
    this.billservice.getBills().subscribe((Response: any) => {
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(Response);
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackbar(this.responseMessage, GlobalConstants.error);
    });

  }
  applyFilter(event: Event) {

    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleViewAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      data: values

    };
    dialogConfig.width = "100%";
    const dialogRef = this.dialog.open(ViewBillProductsComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();


    });
  }

  handleDeleteAction(values: any) {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'delete' + +" " + values.name + "  " + 'bill',
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((Response => {
      this.ngxService.start();
      this.deleteProduct(values.id);
      dialogRef.close();

    }))

  }


  deleteProduct(id: any) {
    this.billservice.delete(id).subscribe((Response: any) => {
      this.ngxService.stop();
      this.tableData();
      this.responseMessage = Response?.message;
      this.snackbarService.openSnackbar(this.responseMessage, "success");


    }, (error: any) => {
      this.ngxService.stop()
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;

      }
      this.snackbarService.openSnackbar(this.responseMessage, GlobalConstants.error);
    })
  }


  downloadReportAction(values: any) {

    this.ngxService.start();
    var data = {
      name: values.name,
      email: values.email,
      uuid:values.uuid,
      contactNumber:values.contactNumber,
      paymentMethod:values.paymentmethod,
      totalAmount:values.total.toString(),
      productDetails:values.productDetails
  }
    this.downloadFile(values.uuid);
  }

  downloadFile(filename:any ) {
    var data={
      uuid:filename,
     
    }
    
    this.billservice.getPdf(data).subscribe((response:any)=>{
     saveAs(response,filename+'.pdf');
     this.ngxService.stop();
 
    })

}
}
