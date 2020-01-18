import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class CanActivateUserGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService, private router: Router,
              private toastr: ToastrService) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    // alert('next: ' + next.url + ', state: ' + state.url);
    if (this.authenticationService.isLoggedIn()) {
      const roles: any[] = this.authenticationService.getCurrentUser().roles;

      const requiredRoles = next.data.expectedRoles; // ovo "data.expectedRoles" smo mi definisali u app-routing.module.ts
      let granted = false;
      if (!requiredRoles || requiredRoles.length === 0) {
        granted = true;
      } else {
        for (const requiredRole of requiredRoles) {
          if (roles.includes(requiredRole)) {
            granted = true;
            break;
          }
        }
      }

      if (granted) {
        return true;
      }
    }

    this.router.navigate(['/home-page']);
    this.toastr.info('Please log in.');
    return false;
  }
}

