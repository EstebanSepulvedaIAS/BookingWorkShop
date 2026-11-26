import { UserRole } from "./user-role.type";

export interface AuthSession{
    readonly username: string;
    readonly token: string;
    readonly roles: readonly UserRole[];
    readonly message?: string;
}