import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { TokenStorageRepository } from "../../features/auth/domain/repositories/token-storage.repository";
import { AuthStore } from "../../features/auth/application/state/auth.store";

export const guestGuard: CanActivateFn = () => {
    const tokenStorage = inject(TokenStorageRepository);
    const authStore = inject(AuthStore);
    const router = inject(Router);

    if(tokenStorage.getToken() && authStore.isAuthenticated()){
        const target = authStore.hasAnyRole(['ROLE_ADMIN', 'ROLE_GESTOR'])
            ? '/roles'
            : '/dashboard';
        return router.createUrlTree([target]);
    }

    return true;
};