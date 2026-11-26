import { HttpErrorResponse } from "@angular/common/http";
import { Component, inject, signal } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { RouterLink, Router } from "@angular/router";
import { finalize } from "rxjs";
import { CreateRoleUseCase } from "../../../application/use-cases/create-role.use-case";

@Component({
    selector: 'app-role-create-page',
    standalone: true,
    imports: [ReactiveFormsModule, RouterLink],
    templateUrl: './roles-create-page.component.html',
    styleUrl: './roles-create-page.component.css'
})
export class RoleCreatePageComponent {
    private readonly fb = inject(FormBuilder);
    private readonly createRoleUseCase = inject(CreateRoleUseCase);
    private readonly router = inject(Router);

    readonly isSubmitting = signal(false);
    readonly errorMessage = signal<string | null>(null);

    readonly form = this.fb.nonNullable.group({
        name: ['', [Validators.required, Validators.pattern(/\S+/)]],
    });

    onSubmit(): void {
        if(this.form.invalid || this.isSubmitting()) {
            this.form.markAllAsTouched();
            return;
        }

        this.isSubmitting.set(true);
        this.errorMessage.set(null);

        const { name } = this.form.getRawValue();

        this.createRoleUseCase
        .execute(name)
        .pipe(finalize(() => this.isSubmitting.set(false)))
        .subscribe({
            next: () => {
                this.router.navigate(['/roles/list']);
            },
            error: (err: HttpErrorResponse) => {
                if (err.status === 409) {
                    this.errorMessage.set('Ya existe un rol con ese nombre.');
                } else if (err.status === 400){
                    this.errorMessage.set('El nombre del rol no es válido.');
                }else{
                    this.errorMessage.set('No se pudo crear el rol. Intenta de nuevo.');
                }
            },
        });
    }
}