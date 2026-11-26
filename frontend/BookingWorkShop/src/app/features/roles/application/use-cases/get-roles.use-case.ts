import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { Role } from "../../domain/models/role.model";
import { RoleRepository } from "../../domain/repositories/role.repository";

@Injectable({ providedIn: 'root' })
export class GetRoleUseCase {
    private readonly roleRepository = inject(RoleRepository);

    execute(): Observable<Role[]> {
        return this.roleRepository.getAll();
    }
}