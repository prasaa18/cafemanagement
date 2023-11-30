import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoryService } from 'src/app/services/category.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoryComponent } from '../dialog/category/category.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-category',
  templateUrl: './manage-category.component.html',
  styleUrls: ['./manage-category.component.scss']
})
export class ManageCategoryComponent implements OnInit {

  displayedColumns: string[] = ['name', 'edit'];
 
  responseMessage: any;
  dataSource: any;

  constructor(
    private  dialog:MatDialog,
    private categoryService: CategoryService,
    private ngxService: NgxUiLoaderService,
    private snackbarService: SnackbarService, private router:Router
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.categoryService.getCategory().subscribe((Response: any) => {
      this.ngxService.stop();
      this.dataSource= new MatTableDataSource(Response);
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

  handleAddAction() {
    // Implement the logic for adding a category here
    const dialogConfig=new MatDialogConfig();
     dialogConfig.data={

     action:'Add'
    };
    dialogConfig.width="850px";
    const dialogRef=this.dialog.open(CategoryComponent,dialogConfig);
    this.router.events.subscribe(()=>{
      dialogRef.close();
 

    });
    const sub=dialogRef.componentInstance.onAddCategory.subscribe((Response)=>{
      this.tableData();
    })
    
  }

  handleEditAction(values: any) {
    // Implement the logic for editing a category here, using the 'data' parameter

    const dialogConfig=new MatDialogConfig();
    dialogConfig.data={
   
        action:'Edit',
        data:values
       };
       dialogConfig.width="850px";
       const dialogRef=this.dialog.open(CategoryComponent,dialogConfig);
       this.router.events.subscribe(()=>{
         dialogRef.close();
    
   
       });
       const sub=dialogRef.componentInstance.onEditCategory.subscribe((Response)=>{
         this.tableData();
       })
  }
}
