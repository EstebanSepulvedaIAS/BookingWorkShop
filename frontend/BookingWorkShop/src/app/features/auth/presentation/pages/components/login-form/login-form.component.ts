import { Component, inject, input, output } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { AuthCredentials } from "../../../../domain/models/auth-credentials.model";
import { validate } from "@angular/forms/signals";

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './login-form.component.html',
})
export class LoginFormComponent {
    private readonly fb = inject(FormBuilder);

    readonly isLoading = input<boolean>(false);
    readonly errorMessage = input<string | null>(null);
    readonly submitCredentials = output<AuthCredentials>();

    readonly form = this.fb.nonNullable.group({
        username: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]],
    });

    onSubmit(): void {
        if(this.form.invalid){
            this.form.markAllAsTouched();
            return;
        }
        this.submitCredentials.emit(this.form.getRawValue());
    }
}