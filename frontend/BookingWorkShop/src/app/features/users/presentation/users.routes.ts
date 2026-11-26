import { Routes } from "@angular/router";

export const USERS_ROUTES: Routes = [
    {
        path: '',
        data: { breadcrumb: 'Usuarios' },
        loadComponent: () => 
            import('./pages/users-list-page/users-list-page.component').then(
                (m) => m.UserListPageComponent,
            ),
    },
    {
        path: 'list',
        data: { breadcrumb: 'Mostrar usuarios' },
        loadComponent: () => 
            import('./pages/users-list-page/users-list-page.component').then(
                (m) => m.UserListPageComponent,
            ),
    },
];