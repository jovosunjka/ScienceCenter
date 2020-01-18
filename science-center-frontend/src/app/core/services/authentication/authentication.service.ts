import { Injectable, HostListener } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GenericService } from '../generic/generic.service';
import { JwtUtilsService } from '../jwt-utils/jwt-utils.service';
import { map, catchError } from 'rxjs/operators';



@Injectable()
export class AuthenticationService {
  private relativeUrl;
  private currentUserKey = 'currentUser';

  constructor(private loginService: GenericService, private jwtUtilsService: JwtUtilsService) {
    this.relativeUrl = '/users/login';
  }


  login(username: string, password: string): Observable<boolean> {
    return this.loginService.put(this.relativeUrl, JSON.stringify({username, password})).pipe(
      map(
        (res: any) => {
          const token = res && res.token;
          if (token) {
            localStorage.setItem(this.currentUserKey, JSON.stringify({username, password,
                  roles: this.jwtUtilsService.getRoles(token), token}));
            return true;
          } else {
            return false;
          }
        }
      ),
      catchError((err: any) => {
        if (err.status === 400 ) {
          return Observable.throw('Ilegal login');
        } else {
          return Observable.throw(err.json().error || 'Server error');
        }
      }));
  }


  getToken(): string {
    const currentUser = JSON.parse(localStorage.getItem(this.currentUserKey));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  logout(): void {
    localStorage.removeItem(this.currentUserKey);
  }

  isLoggedIn(): boolean {
    if (this.getToken() !== '') {
      return true;
    }
    return false;
  }

  getCurrentUser() {
    if (localStorage.currentUser) {
      return JSON.parse(localStorage.currentUser);
    } else {
      return undefined;
    }
  }

}
