import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { environment } from "../../../../../environments/environment";
import { CreateRoleInput } from "../../domain/models/create-role.model";
import { Role } from "../../domain/models/role.model";
import { RoleRepository } from "../../domain/repositories/role.repository";
import { CreateRoleRequestDto } from "../dtos/create-role-request.dto";
import { RoleResponseDto } from "../dtos/role-response.dto";
import { RoleMapper } from "../mappers/role.mapper";

@Injectable()
export class RoleHttpRepository extends RoleRepository {
    private readonly http = inject(HttpClient);
    private readonly endpoint = `${environment.apiUrl}/api/roles`;

    getAll(): Observable<Role[]> {
        return this.http
        .get<RoleResponseDto[]>(this.endpoint)
        .pipe(map((dtos) => RoleMapper.toDomainList(dtos)));
    }

    create(role: CreateRoleInput): Observable<Role> {
        const body: CreateRoleRequestDto = {
            id: role.id,
            name: role.name,
        };

        return this.http
        .post<RoleResponseDto | null>(this.endpoint, body)
        .pipe(map((dto) => RoleMapper.toDomain(dto?.id && dto?.name ? dto : body)));
    }
}