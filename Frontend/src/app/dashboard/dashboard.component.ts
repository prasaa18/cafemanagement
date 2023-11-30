import { Component, AfterViewInit } from '@angular/core';
import { DashboardService } from '../services/dashboard.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { error } from 'console';
import { GlobalConstants } from '../shared/global-constants';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit {


	responceMessage:any;
	data:any;
 
	ngAfterViewInit() { }

	constructor(private dashboardService:DashboardService,
		private ngxService:NgxUiLoaderService,
		private snackBarService:SnackbarService,private router:Router) {

			this.ngxService.start();
			this.dashboardData();
	}



	dashboardData(){
		this.dashboardService.getDetais().subscribe((responce:any)=>{

			this.ngxService.stop();
			this.data=responce;

		},(error:any)=>{

			this.ngxService.stop();
			console.log(error);
			if(error.error?.message){
				this.responceMessage=error.error?.message;
			}
			else{
				this.responceMessage=GlobalConstants.genericError;
			}

			this.snackBarService.openSnackbar(this.responceMessage,GlobalConstants.error);

		}


		)

	}

}