import { inject } from '@angular/core';
import { CanActivateFn, Router} from '@angular/router';
import { AuthStore } from '../../features/auth/application/state/auth.store';
import { UserRole } from '../../features/auth/domain/models/user-role.type';

export const roleGuard: CanActivateFn = (route) => {
  const authStore = inject(AuthStore);
  const router = inject(Router);

  const allowedRoles = (route.data?.['roles'] as UserRole[] | undefined) ?? [];

  if(allowedRoles.length === 0){
    return true;
  }
  return authStore.hasAnyRole(allowedRoles) ? true : router.createUrlTree(['/dashboard']);
};