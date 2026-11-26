import { Component, computed, DestroyRef, inject, OnInit, signal } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { Router, ActivatedRoute, NavigationEnd, RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";
import { filter } from "rxjs";
import { AuthStore } from "../../../features/auth/application/state/auth.store";

@Component({
    selector: 'app-main-layout',
    standalone: true,
    imports: [RouterOutlet, RouterLink, RouterLinkActive],
    templateUrl: './main-layout.component.html',
    styleUrl: './main-layout.component.css'
})
export class MainLayoutComponent implements OnInit{
    readonly authStore = inject(AuthStore);
    private readonly router = inject(Router);
    private readonly activatedRoute = inject(ActivatedRoute);
    private readonly destroyRef = inject(DestroyRef);

    readonly canAccessRoles = computed(() =>
        this.authStore.hasAnyRole(['ROLE_ADMIN', 'ROLE_GESTOR']),
    );

    readonly currentBreadcrumb = signal<string>('');

    ngOnInit(): void {
        this.updateBreadcrumb();

        this.router.events
        .pipe(
            filter((event) => event instanceof NavigationEnd),
            takeUntilDestroyed(this.destroyRef),
        )
        .subscribe(() => this.updateBreadcrumb());
    }

    private updateBreadcrumb(): void {
        let route = this.activatedRoute;
        while(route.firstChild){
            route = route.firstChild;
        }    
        this.currentBreadcrumb.set((route.snapshot.data['breadcrumb'] as string) ?? '');
    }
}