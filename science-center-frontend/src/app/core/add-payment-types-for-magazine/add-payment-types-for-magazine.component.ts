import { Component, OnInit } from '@angular/core';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';
import { Router } from '@angular/router';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-payment-types-for-magazine',
  templateUrl: './add-payment-types-for-magazine.component.html',
  styleUrls: ['./add-payment-types-for-magazine.component.css']
})
export class AddPaymentTypesForMagazineComponent implements OnInit {
  private processInstanceId: string;
  private relativeUrlForAddEditroaAndReviewers = '/magazines/add-payment-types';


  formFieldsForPaymentTypes: any[];
  private relativeUrlForFormFieldsForPaymentTypes = '/payment/form-fields-for-payment-types';

  constructor(private genericService: GenericService, private forwardingMessageService: ForwardingMessageService,
              private router: Router, private toastr: ToastrService) {
      this.formFieldsForPaymentTypes = [];
  }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');

    this.getFormFieldsForPaymentTypes();

    this.forwardingMessageService.addPaymentTypesEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.router.navigate(['/editor-page']);
        }
      }
    );
  }

  getFormFieldsForPaymentTypes() {
    this.genericService.get<any[]>(this.relativeUrlForFormFieldsForPaymentTypes)
      .subscribe(
        (formFieldsForPaymentTypes: any[]) => {
            this.formFieldsForPaymentTypes = formFieldsForPaymentTypes;
            this.formFieldsForPaymentTypes.forEach(
              paymentType => paymentType.formFields.forEach(field => {
                if (field.type === 'string') {
                  field.value = '';
                } else if (field.type === 'boolean') {
                  field.value = false;
                } else if (field.type === 'long') {
                  field.value = 0;
                } else {
                  field.value = null;
                }
              })
            );
            this.toastr.success('Form fields for payment types loaded!');
        },
        err => alert(JSON.stringify(err))
      );
  }

  finish() {
    let paymentTypes = '';
    let numOfFilledFields;
    let haveLeastOnePaymentType = false;
    const messages = [];
    this.formFieldsForPaymentTypes.forEach(fffptp => {
        paymentTypes += '|' + fffptp.paymentType + ';';
        let fields = '';
        numOfFilledFields = 0;
        fffptp.formFields.forEach(field => {
            // if (field.value) {
              field.value = field.value.trim();
              if (field.value !== '') {
                fields +=  ',' + field.name + ':' + field.value;
                numOfFilledFields++;
              } else if (field.optional) {
                numOfFilledFields++;
              }
            // }
          }
        );
        if (numOfFilledFields > 0 && numOfFilledFields < fffptp.formFields.length) {
            messages.push('You have not filled all the fields for '.concat(fffptp.paymentType));
            return;
        } else if (numOfFilledFields === fffptp.formFields.length) {
            haveLeastOnePaymentType = true;
        }
        paymentTypes += fields.substring(1);
      }
    );

    if (!haveLeastOnePaymentType) {
      this.toastr.error('You must complete at least one type of payment');
      return;
    } else {
      if (messages.length > 0) {
        messages.forEach(m => this.toastr.error(m));
        return;
      }
    }

    paymentTypes = paymentTypes.substring(1);

    this.genericService.put<any>(this.relativeUrlForAddEditroaAndReviewers.concat('?processInstanceId=')
              .concat(this.processInstanceId), {paymentTypes})
          .subscribe(
            () => this.toastr.success('The form was successfully submitted!'),
            err => alert(JSON.stringify(err))
          );
    }

}
