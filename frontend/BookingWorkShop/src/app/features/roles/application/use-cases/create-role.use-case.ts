import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { Role } from "../../domain/models/role.model";
import { RoleRepository } from "../../domain/repositories/role.repository";

@Injectable({ providedIn: 'root' })
export class CreateRoleUseCase {
    private readonly roleRepository = inject(RoleRepository);

    execute(name: string): Observable<Role> {
        return this.roleRepository.create({
            id: crypto.randomUUID(),
            name: name.trim(),
        });
    }
}