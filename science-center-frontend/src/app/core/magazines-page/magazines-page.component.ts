import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { Magazine } from 'src/app/shared/model/magazine';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';

@Component({
  selector: 'app-magazines-page',
  templateUrl: './magazines-page.component.html',
  styleUrls: ['./magazines-page.component.css']
})
export class MagazinesPageComponent implements OnInit {

  private magazines: Magazine[];

  private relativeUrlForAllActivatedMagazines = '/magazines/all-activated';

  constructor(private genericService: GenericService, private toastr: ToastrService,  private forwardingMessageService: ForwardingMessageService) {
    this.magazines = [];
   }

  ngOnInit() {
    this.getAllActivatedMagazines();

    this.forwardingMessageService.activateMagazineEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.toastr.success('Activating of magazine is done!');
          this.getAllActivatedMagazines();
        }
      }
    );
  }

  getAllActivatedMagazines() {
    this.genericService.get<Magazine[]>(this.relativeUrlForAllActivatedMagazines).subscribe(
      (magazines: Magazine[]) => {
        this.magazines = magazines;
        this.toastr.success('Magazines loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading magazines!');
      }
    );
  }

  showCreateMagazineButton() {
    return window.location.href.endsWith('magazines-page');
  }

}
