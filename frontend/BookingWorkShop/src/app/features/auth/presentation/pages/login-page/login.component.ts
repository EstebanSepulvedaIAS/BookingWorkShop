import { HttpErrorResponse } from "@angular/common/http";
import { Component, inject, signal } from "@angular/core";
import { AuthStore } from "../../../application/state/auth.store";
import { Router } from "@angular/router";
import { finalize } from "rxjs";
import { LoginUseCase } from "../../../application/use-cases/login.use-case";
import { AuthCredentials } from "../../../domain/models/auth-credentials.model";
import { LoginFormComponent } from "../components/login-form/login-form.component";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [LoginFormComponent],
  templateUrl: './login.component.html',
})
export class LoginPageComponent {
  private readonly loginUseCase = inject(LoginUseCase);
  private readonly authStore = inject(AuthStore);
  private readonly router = inject(Router);

  readonly isLoading = signal(false);
  readonly errorMessage = signal<string | null>(null);

  onLogin(credentials: AuthCredentials): void{
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.loginUseCase
    .execute(credentials)
    .pipe(finalize(() => this.isLoading.set(false)))
    .subscribe({
      next: () => {
        const target = this.authStore.hasAnyRole(['ROLE_ADMIN', 'ROLE_GESTOR'])
          ? '/roles'
          : '/dashboard';
        this.router.navigate([target]);
      },
      error: (err: HttpErrorResponse) => {
        if(err.status === 401 || err.status === 403){
          this.errorMessage.set('Credenciales incorrectas.');
        }else{
          this.errorMessage.set('No se pudo conectar con el servidor. Intenta de nuevo');
        }
      },
    });
  }
}