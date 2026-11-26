import { Routes } from "@angular/router";

export const ROLES_ROUTES: Routes = [
    {
        path: '',
        data: { breadcrumb: 'Roles'},
        loadComponent: () =>
            import('./pages/roles-hub-page/roles-hub-page.component').then(
                (m) => m.RolesHubPageComponent,
            ),
    },
    {
        path: 'list',
        data: { breadcrumb: 'Mostrar roles' },
        loadComponent: () => 
            import('./pages/roles-list-page/roles-list-page.component').then(
                (m) => m.RoleListPageComponent,
            ),
    },
    {
        path: 'create',
        data: { breadcrumb: 'Crear rol' },
        loadComponent: () =>
            import('./pages/roles-create-page/roles-create-page.component').then(
                (m) => m.RoleCreatePageComponent,
            ),
    },
];