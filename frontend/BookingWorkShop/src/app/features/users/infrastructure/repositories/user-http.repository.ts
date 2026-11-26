import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable, map } from "rxjs";
import { environment } from "../../../../../environments/environment";
import { User } from "../../domain/models/user.model";
import { UserRepository } from "../../domain/repositories/user.repository";
import { UserResponseDto } from "../dtos/user-response.dto";
import { UserMapper } from "../mappers/user.mapper";

@Injectable()
export class UserHttpRepository extends UserRepository {
    private readonly http = inject(HttpClient);
    private readonly endpoint = `${environment.apiUrl}/api/users`;

    getAll(): Observable<User[]> {
        return this.http
            .get<UserResponseDto[]>(this.endpoint)
            .pipe(map((dtos) => UserMapper.toDomainList(dtos)));
    }
}