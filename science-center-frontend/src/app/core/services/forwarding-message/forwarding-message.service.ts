import { Injectable, Output, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ForwardingMessageService {

  @Output()
  registerPageEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  confirmRegistrationEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  mailSentEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  confirmReviewerStatusEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  saveNewMagazineEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  addEditorsAndReviewersEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  addPaymentTypesEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Output()
  activateMagazineEvent: EventEmitter<boolean> = new EventEmitter<boolean>();


  constructor() { }

  sendMessageToRegisterPage(hasError: boolean) {
    this.registerPageEvent.emit(hasError);
  }

  sendMessageToConfirmRegistration(hasError: boolean) {
    this.confirmRegistrationEvent.emit(hasError);
  }

  sendMessageToLoginPage(hasError: boolean) {
    this.mailSentEvent.emit(hasError);
  }

  sendMessageToRequestsForReviewer(hasError: boolean) {
    this.confirmReviewerStatusEvent.emit(hasError);
  }

  sendMessageToSaveNewMagazine(hasError: boolean) {
    this.saveNewMagazineEvent.emit(hasError);
  }

  sendMessageToAddEditorsAndReviewersEvent(hasError: boolean) {
    this.addEditorsAndReviewersEvent.emit(hasError);
  }

  sendMessageToAddPaymentTypes(hasError: boolean) {
    this.addPaymentTypesEvent.emit(hasError);
  }

  sendMessageToActivateMagazine(hasError: boolean) {
    this.activateMagazineEvent.emit(hasError);
  }

}
