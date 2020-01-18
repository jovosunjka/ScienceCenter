import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { filter } from 'rxjs/operators';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';


@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.component.html',
  styleUrls: ['./confirm-registration.component.css']
})
export class ConfirmRegistrationComponent implements OnInit {
  private processInstanceId: string;

  confirmationStatus = 'Wait...';

  private relativeUrlForConfirmation = '/users/confirm-registration';
  private token: string;

  constructor(private genericService: GenericService, private route: ActivatedRoute, private toastr: ToastrService,
              private forwardingMessageService: ForwardingMessageService) { }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');

    // https://alligator.io/angular/query-parameters/
    this.route.queryParams.pipe(
      filter(params => params.token)
    )
    .subscribe(params => {
        console.log(params);
        this.token = params.token;
      }
    );

    if (this.token) {
      setTimeout(() => this.confirm(), 1000); // cekamo 1 sekund da web socket stigao da se konektuje i subscribe-uje na server
      // this.confirm();
    } else {
      this.confirmationStatus = 'Token not found!';
    }

    this.forwardingMessageService.confirmRegistrationEvent.subscribe(
      (hasError: boolean) => {
        if (hasError) {
          this.confirmationStatus = 'confirmation failed';
        } else {
          this.confirmationStatus = 'confirmation successful';
        }
      }
    );
  }

  confirm() {
      this.genericService.put<any>(this.relativeUrlForConfirmation.concat('?processInstanceId=').concat(this.processInstanceId),
                          {token: this.token} )
            .subscribe(
              () => this.toastr.info('Confirmation started'),
              err => alert(JSON.stringify(err))
            );
  }

}
