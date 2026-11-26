export type RoleBadgeTheme = 'admin' | 'gestor' | 'default';

export interface Role {
    readonly id: string;
    readonly name: string;
    readonly description: string;
}