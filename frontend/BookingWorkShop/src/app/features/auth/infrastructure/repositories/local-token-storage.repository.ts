import { isPlatformBrowser } from "@angular/common";
import { inject, Injectable, PLATFORM_ID } from "@angular/core";
import { TokenStorageRepository } from "../../domain/repositories/token-storage.repository";

const TOKEN_KEY = 'auth_token';

@Injectable()
export class LocalTokenStorageRepository extends TokenStorageRepository{
    private readonly platformId = inject(PLATFORM_ID);

    saveToken(token: string): void {
        if(isPlatformBrowser(this.platformId)){
            localStorage.setItem(TOKEN_KEY, token);
        }    
    }

    getToken(): string | null {
        if(isPlatformBrowser(this.platformId)){
            return localStorage.getItem(TOKEN_KEY);
        }
        return null;
    }

    clearToken(): void {
        if(isPlatformBrowser(this.platformId)){
            localStorage.removeItem(TOKEN_KEY);    
        }
    }
}