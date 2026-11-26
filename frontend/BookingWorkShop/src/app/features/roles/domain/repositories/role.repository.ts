import { Observable } from "rxjs";
import { CreateRoleInput } from "../models/create-role.model";
import { Role } from "../models/role.model";

export abstract class RoleRepository {
    abstract getAll(): Observable<Role[]>;
    abstract create(role: CreateRoleInput): Observable<Role>; 
}