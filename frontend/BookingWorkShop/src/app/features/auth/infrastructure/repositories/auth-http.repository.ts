import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { environment } from "../../../../../environments/environment";
import { AuthCredentials } from "../../domain/models/auth-credentials.model";
import { AuthSession } from "../../domain/models/auth-session.model";
import { AuthRepository } from "../../domain/repositories/auth.reposiroty";
import { LoginRequestDto } from "../dtos/login-request.dto";
import { LoginResponseDto } from "../dtos/login-response.dto";
import { AuthMapper } from "../mappers/auth.mapper";

@Injectable()
export class AuthHttpRepository extends AuthRepository{
    private readonly http = inject(HttpClient);
    private readonly endpoint = `${environment.apiUrl}/auth/login`;

    login(credentials: AuthCredentials): Observable<AuthSession>{
        const body: LoginRequestDto = {
            username: credentials.username,
            password: credentials.password,
        };
        
        return this.http
        .post<LoginResponseDto>(this.endpoint, body)
        .pipe(map((dto) => AuthMapper.toDomain(dto)));
    }
}