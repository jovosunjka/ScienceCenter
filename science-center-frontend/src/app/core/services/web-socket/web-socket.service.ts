import { Injectable, Inject } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { ToastrService } from 'ngx-toastr';
import { ForwardingMessageService } from '../forwarding-message/forwarding-message.service';


@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private stompClient;
  private serverSocketRelativeUrl = '/socket';
  private subscription;
  private successfullyConnected: boolean;


  constructor(@Inject('BASE_API_URL') private baseUrl: string, private toastr: ToastrService,
              private forwardingMessageService: ForwardingMessageService) {
  }

  initializeWebSocketConnection() {
    const socket = new SockJS(this.baseUrl + this.serverSocketRelativeUrl);
    this.stompClient = Stomp.over(socket);
    this.connect();

  }

  connect() {
      // prva lambda funkcija je callback za uspesno konektovanje, a druga lambda fukcija za pucanje konekcije
    this.stompClient.connect({},
      (frame) =>  this.subscribe(),
      () => {
        this.toastr.error('The connection with the science-center-backend was interrupted!');
        this.toastr.info('We will try to reconnect.');
        this.connect();
      }
    );
  }

  subscribe() {
      this.subscription = this.stompClient.subscribe('/topic', (message) => {
        if (message.body) {
          const body = JSON.parse(message.body);

          if (body.type === 'register-page') {
            this.forwardingMessageService.sendMessageToRegisterPage(body.hasError);
          } else if (body.type === 'confirm-registration') {
            this.forwardingMessageService.sendMessageToConfirmRegistration(body.hasError);
          } else if (body.type === 'mail sent') {
            this.forwardingMessageService.sendMessageToLoginPage(body.hasError);
          } else if (body.type === 'confirm-reviewer-status') {
            this.forwardingMessageService.sendMessageToRequestsForReviewer(body.hasError);
          } else if (body.type === 'save-new-magazine') {
            this.forwardingMessageService.sendMessageToSaveNewMagazine(body.hasError);
          } else if (body.type === 'save-editors-and-reviewers') {
            this.forwardingMessageService.sendMessageToAddEditorsAndReviewersEvent(body.hasError);
          } else if (body.type === 'save-payment-types') {
            this.forwardingMessageService.sendMessageToAddPaymentTypes(body.hasError);
          } else if (body.type === 'activate-magazine') {
            this.forwardingMessageService.sendMessageToActivateMagazine(body.hasError);
          } else if (body.type === 'check-author-has-activated-membership-fee') {
            this.forwardingMessageService.sendMessageToMagazinesPageCAHAMF(body.hasError);
          } else if (body.type === 'save-new-scientific-paper') {
            this.forwardingMessageService.sendMessageToAddNewScientificPaper(body.hasError);
          }

          if (body.hasError) {
            this.toastr.error(body.text);
          } else {
            this.toastr.success(body.text);
          }
        } else {
          this.toastr.error('Empty body in a websocket message!');
        }
      });
  }

  unsubscribe() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  disconnect() {
    if (this.stompClient) {
      if (this.successfullyConnected) {
        this.stompClient.disconnect(
          () => this.toastr.info('The connection with the science-center-backend was interrupted!')
        );
      }
    }
  }


}
