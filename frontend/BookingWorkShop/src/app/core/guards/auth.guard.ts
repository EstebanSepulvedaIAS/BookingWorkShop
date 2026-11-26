import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStore } from '../../features/auth/application/state/auth.store';
import { TokenStorageRepository } from '../../features/auth/domain/repositories/token-storage.repository';

export const authGuard: CanActivateFn = () => {
  const tokenStorage = inject(TokenStorageRepository);
  const authStore = inject(AuthStore);
  const router = inject(Router);

  if(tokenStorage.getToken() && authStore.isAuthenticated()){
    return true;
  }

  tokenStorage.clearToken();
  authStore.clearSession();
  return router.createUrlTree(['/auth/login']);
};