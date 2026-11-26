import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { AuthCredentials } from "../../domain/models/auth-credentials.model";
import { AuthSession } from "../../domain/models/auth-session.model";
import { AuthRepository } from "../../domain/repositories/auth.reposiroty";
import { TokenStorageRepository } from "../../domain/repositories/token-storage.repository";
import { AuthStore } from "../state/auth.store";

@Injectable({providedIn: 'root'})
export class LoginUseCase {
  private readonly authRepository = inject(AuthRepository);
  private readonly tokenStorage = inject(TokenStorageRepository);
  private readonly authStore = inject(AuthStore);

  execute(credentials: AuthCredentials): Observable<AuthSession>{
    return this.authRepository.login(credentials).pipe(
      tap((session) => {
        this.tokenStorage.saveToken(session.token);
        this.authStore.setSession(session);
      }),
    );
  }
}