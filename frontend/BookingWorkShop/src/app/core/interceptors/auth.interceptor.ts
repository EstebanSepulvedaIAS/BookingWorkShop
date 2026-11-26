import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { catchError, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { AuthStore } from "../../features/auth/application/state/auth.store";
import { TokenStorageRepository } from "../../features/auth/domain/repositories/token-storage.repository";
import path from "path";

const PUBLIC_ENDPOINTS = ['/auth/login'];

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const isApiRequest = req.url.startsWith(environment.apiUrl);
    const isPublicEndpoint = PUBLIC_ENDPOINTS.some((path) => req.url.includes(path));

    if(!isApiRequest || isPublicEndpoint){
        return next(req);
    }

    const tokenStorage = inject(TokenStorageRepository);
    const authStore = inject(AuthStore);
    const router = inject(Router);

    const token = tokenStorage.getToken();

    const authReq = token
        ? req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`,
            },
        })
        : req;
    
    return next(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
            if(error.status === 401){
                tokenStorage.clearToken();
                authStore.clearSession();
                router.navigate(['/auth/login']);
            }
            return throwError(() => error);
        }),
    );
};