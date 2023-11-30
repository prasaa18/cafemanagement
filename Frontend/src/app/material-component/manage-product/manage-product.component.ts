import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { error } from 'console';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ProductComponent } from '../dialog/product/product.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-manage-product',
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.scss']
})
export class ManageProductComponent implements OnInit {

  displayedColumn: string[] = ['name', 'categoryName', 'discription', 'price', 'edit'];
  dataSource: any;
  //length1:any;
  responseMessage: any;

  constructor(private productServiec: ProductService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackBarService: SnackbarService,
    private router: Router) { }

  ngOnInit(): void {

    this.ngxService.start();
    this.tableData();

  }

  tableData() {
    this.productServiec.getProduct().subscribe((response: any) => {
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(response);

    }, (error: any) => {
      this.ngxService.stop()
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;

      }
      this.snackBarService.openSnackbar(this.responseMessage, GlobalConstants.error);
    })
  }
  applyFilter(event: Event) {

    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();


  }


  handleAddAction() {
    // Implement the logic for adding a category here
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {

      action: 'Add'
    };
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(ProductComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();


    });
    const sub = dialogRef.componentInstance.onAddProduct.subscribe((Response) => {
      this.tableData();
    })

  }

  handleEditAction(values: any) {
    // Implement the logic for editing a category here, using the 'data' parameter

    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {

      action: 'Edit',
      data: values
    };
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(ProductComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();


    });
    const sub = dialogRef.componentInstance.onEditProduct.subscribe((Response) => {
      this.tableData();
    })


  }

  handledeleteAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'delete' + values.name + " "+'product',
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
    this.productServiec.delete(id).subscribe((Response: any) => {
      this.ngxService.stop();
      this.tableData();
      this.responseMessage = Response?.message;
      this.snackBarService.openSnackbar(this.responseMessage, "success");


    }, (error: any) => {
      this.ngxService.stop()
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;

      }
      this.snackBarService.openSnackbar(this.responseMessage, GlobalConstants.error);
    })
  }




  onChange(status: any, id: any) {
    this.ngxService.start();
    var data = {
      status: status.toString(),
      id: id,

    }
    this.productServiec.updateStatus(data).subscribe((Response: any) => {
      this.ngxService.stop();
      this.responseMessage = Response?.message;
      this.snackBarService.openSnackbar(this.responseMessage, 'success');
    }, (error: any) => {
      this.ngxService.stop()
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;

      }
      this.snackBarService.openSnackbar(this.responseMessage, GlobalConstants.error);
    })
  }

}
