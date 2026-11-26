import { Component, inject, OnInit, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { finalize } from "rxjs";
import { GetRoleUseCase } from "../../../application/use-cases/get-roles.use-case";
import { Role } from "../../../domain/models/role.model";

@Component({
    selector: 'app-roles-list-page',
    standalone: true,
    imports: [RouterLink],
    templateUrl: './roles-list-page.component.html',
    styleUrl: './roles-list-page.component.css'
})
export class RoleListPageComponent implements OnInit {
    private readonly getRoleUseCae = inject(GetRoleUseCase);

    readonly roles = signal<Role[]>([]);
    readonly isLoading = signal(true);
    readonly errorMessage = signal<string | null>(null);
    
    ngOnInit(): void {
        this.loadRoles();
    }

    getInitials(roleName: string): string {
        return roleName.slice(0, 2).toUpperCase();
    }

    getBadgeClass(roleName: string): string {
        if (roleName === 'ADMIN') return 'badge-admin';
        if (roleName === 'GESTOR') return 'badge-gestor';
        return 'badge-default';
    }

    private loadRoles(): void {
        this.isLoading.set(true);
        this.errorMessage.set(null);

        this.getRoleUseCae
        .execute()
        .pipe(finalize(() => this.isLoading.set(false)))
        .subscribe({
            next: (roles) => this.roles.set(roles),
            error: () => {
                this.errorMessage.set('No se pudieron cargar los roles. Intenta nuevamente');
            },
        });
    }
}