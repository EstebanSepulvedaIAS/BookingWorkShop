import { Component, inject } from "@angular/core";
import { AuthStore } from "../../../application/state/auth.store";

@Component({
    selector: 'app-dashboard-page',
    standalone: true,
    template: `
        <main>
            <h1>Sesión iniciada</h1>
            <p><strong>Usuario:</strong> {{ authStore.username() }}</p>
            <p><strong>Roles:</strong> {{ authStore.roles().join(', ') }}</p>
        </main>
    `
})
export class DashboardPageComponent{
    readonly authStore = inject(AuthStore);
}