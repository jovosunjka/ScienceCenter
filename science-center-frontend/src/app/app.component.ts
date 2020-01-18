import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketService } from './core/services/web-socket/web-socket.service';
import { AuthenticationService } from './core/services/authentication/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Science Center';

  constructor(public authenticationService: AuthenticationService, private webSocketService: WebSocketService) {
  }

  ngOnInit() {
    this.webSocketService.initializeWebSocketConnection();
  }

  ngOnDestroy() {
    this.webSocketService.unsubscribe();
    this.webSocketService.disconnect();
  }
}
