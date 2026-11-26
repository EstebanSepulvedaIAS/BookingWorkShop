import { computed, Injectable, signal } from "@angular/core";
import { AuthSession } from "../../domain/models/auth-session.model";
import { UserRole } from "../../domain/models/user-role.type";

@Injectable({providedIn: 'root'})
export class AuthStore{
    private readonly _session = signal<AuthSession | null>(null);

    readonly session = this._session.asReadonly();
    readonly username = computed(() => this._session()?.username ?? '');
    readonly roles = computed(() => this._session()?.roles ?? []);
    readonly isAuthenticated = computed(() => this._session() !== null);

    readonly primaryRoleLabel = computed(() => {
        const firstRole = this.roles()[0];
        if(!firstRole) return '';
        return firstRole.replace(/^ROLE_/i, '').toLowerCase();
    });

    readonly userInitials = computed(() => {
        const email = this.username().trim();
        return email ? email.slice(0, 2).toUpperCase() : '--';
    })

    setSession(session: AuthSession): void {
        this._session.set(session);
    }

    clearSession(): void {
        this._session.set(null);
    }

    hasRole(role: UserRole): boolean{
        return this.roles().includes(role);
    }

    hasAnyRole(allowedRoles: readonly UserRole[]): boolean {
        return allowedRoles.some((role) => this.roles().includes(role));
    }
}