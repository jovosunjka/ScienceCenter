import { Component, OnInit } from '@angular/core';
import { PendingMagazine } from 'src/app/shared/model/pending-magazine';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-check-data-for-pending-magazines',
  templateUrl: './check-data-for-pending-magazines.component.html',
  styleUrls: ['./check-data-for-pending-magazines.component.css']
})
export class CheckDataForPendingMagazinesComponent implements OnInit {

  
  private relativeUrlForAllPendingMagazines = '/magazines/all-pending';
  private relativeUrlForCheckData = '/magazines/check-data';

  pendingMagazines: PendingMagazine[];


  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.pendingMagazines = [];
  }

  ngOnInit() {
    this.getPendingMagazines();
  }

  getPendingMagazines() {
    this.genericService.get<PendingMagazine[]>(this.relativeUrlForAllPendingMagazines).subscribe(
      (pendingMagazines: PendingMagazine[]) => {
        this.pendingMagazines = pendingMagazines;
        this.toastr.success('Pending magazines loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading pending magazines!');
      }
    );
  }

  checkData(taskId: string, validData: boolean) {
    this.genericService.put<any>(this.relativeUrlForCheckData.concat('?taskId=' + taskId), validData)
      .subscribe(
        () => {
            this.getPendingMagazines();
            this.toastr.success('Checking data is done!');
        },
        err => {
            this.toastr.error('Problem with checking data!');
        }
      );
  }
}
