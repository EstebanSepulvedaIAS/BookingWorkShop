import { Component, inject, OnInit, signal } from "@angular/core";
import { finalize } from "rxjs";
import { GetUsersUseCase } from "../../../application/use-cases/get-users.use-case";
import { User } from "../../../domain/models/user.model";

@Component({
    selector: 'app-users-list-page',
    standalone: true,
    templateUrl: './users-list-page.component.html',
    styleUrl: './users-list-page.component.css',
})
export class UserListPageComponent implements OnInit {
    private readonly getUserUseCase = inject(GetUsersUseCase);

    readonly users = signal<User[]>([]);
    readonly isLoading = signal(true);
    readonly errorMessage = signal<string | null>(null);

    getInitials(name: string): string {
        const parts = name.trim().split(/\s+/).filter(Boolean);
        if (parts.length === 0) return '--';
        if (parts.length === 1) return parts[0].slice(0,2).toUpperCase();
        return (parts[0][0] + parts[1][0]).toUpperCase();
    }

    ngOnInit(): void {
        this.isLoading.set(true);
        this.errorMessage.set(null);

        this.getUserUseCase
            .execute()
            .pipe(finalize(() => this.isLoading.set(false)))
            .subscribe({
                next: (users) => this.users.set(users),
                error: () => {
                    this.errorMessage.set('No se pudieron cargar los usuarios. Intenta de nuevo.');
                },
            });
    }
}