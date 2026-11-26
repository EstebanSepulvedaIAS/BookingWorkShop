import { Routes } from "@angular/router";
import { authGuard } from "./core/guards/auth.guard";
import { guestGuard } from "./core/guards/guest.guard";
import { roleGuard } from "./core/guards/role.guard";
import { MainLayoutComponent } from "./core/layout/main-layout/main-layout.component";
import { UserRole } from "./features/auth/domain/models/user-role.type";
import path from "path";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full',
  },
  {
    path: 'auth',
    canActivate: [guestGuard],
    loadChildren: () => 
      import('./features/auth/presentation/auth.routes').then((m) => m.AUTH_ROUTES),
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'roles',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN', 'ROLE_GESTOR'] satisfies UserRole[] },
        loadChildren: () =>
          import('./features/roles/presentation/roles.routes').then((m) => m.ROLES_ROUTES),
      },
      {
        path: 'users',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN', 'ROLE_GESTOR'] satisfies UserRole[] },
        loadChildren: () => 
          import('./features/users/presentation/users.routes').then(
            (m) => m.USERS_ROUTES,
          ),
      },
      {
        path: 'dashboard',
        loadComponent: () => 
          import('./features/dashboard/presentation/pages/dashboard-pages/dashboard-page.component').then(
            (m) => m.DashboardPageComponent,
          ),
      },
      {
        path: '',
        redirectTo: 'roles',
        pathMatch: 'full',
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'auth/login',
  },
];
