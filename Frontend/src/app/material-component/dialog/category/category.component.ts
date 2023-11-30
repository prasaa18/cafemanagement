import { Component,EventEmitter, OnInit,Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { error } from 'console';
import { CategoryService } from 'src/app/services/category.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  onAddCategory =new EventEmitter();
  onEditCategory=new EventEmitter();
  catgeoryForm:any=FormGroup;
  dialogAction:any="Add";
  action:any="Add";
  responSeMessage:any;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  private categoryService:CategoryService,
  public dialogRef:MatDialogRef<CategoryComponent>,
  private snackBarService:SnackbarService) { }

  ngOnInit(): void {
   this.catgeoryForm= this.formBuilder.group({
    name:[null,Validators.required]
   });
   if(this.dialogData.action=='Edit')
   {
    this.dialogAction='Edit';
    this.action='update';
    this.catgeoryForm.patchValue(this.dialogData.data);
   }
  }

  handleSubmit(){
    if(this.dialogAction=="Edit"){
      this.edit();

    }
    else{
    this.add();
    }
  }
  add(){

      var  formData=this.catgeoryForm.value;
      var data={
        name:formData.name
      }

      this.categoryService.add(data).subscribe((response:any)=>{
        this.dialogRef.close();
        this.onAddCategory.emit();
        this.responSeMessage=response.message;
        this.snackBarService.openSnackbar(this.responSeMessage,"success");

      },(error)=>{
        this.dialogRef.close();
        console.error(error);
        if(error.error?.message){
           this.responSeMessage=error.error?.message;

        }
        else{
          this.responSeMessage=GlobalConstants.genericError;

          
        }
        this.snackBarService.openSnackbar(this.responSeMessage,GlobalConstants.error);
      });
  }
  edit(){

    var  formData=this.catgeoryForm.value;
    var data={
      id:this.dialogData.data.id,
      name:formData.name
    }

    this.categoryService.update(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onEditCategory.emit();
      this.responSeMessage=response.message;
      this.snackBarService.openSnackbar(this.responSeMessage,"success");

    },(error)=>{
      this.dialogRef.close();
      console.error(error);
      if(error.error?.message){
         this.responSeMessage=error.error?.message;

      }
      else{
        this.responSeMessage=GlobalConstants.genericError;

        
      }
      this.snackBarService.openSnackbar(this.responSeMessage,GlobalConstants.error);
    });

  }

}
