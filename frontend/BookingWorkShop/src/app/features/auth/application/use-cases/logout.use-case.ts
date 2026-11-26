import { inject, Injectable } from '@angular/core';
import { TokenStorageRepository } from '../../domain/repositories/token-storage.repository';
import { AuthRepository } from '../../domain/repositories/auth.reposiroty';
import { AuthStore } from '../state/auth.store';

@Injectable({providedIn: 'root'})
export class LogoutUseCase {
  private readonly authRepository = inject(AuthRepository);
  private readonly tokenStorage = inject(TokenStorageRepository);
  private readonly authStore = inject(AuthStore);

  execute(): void {
    this.tokenStorage.clearToken();
  }
}