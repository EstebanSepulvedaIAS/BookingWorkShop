import { AuthSession } from "../../domain/models/auth-session.model";
import { UserRole } from "../../domain/models/user-role.type";
import { LoginResponseDto } from "../dtos/login-response.dto";

const VALID_ROLES: ReadonlySet<string> = new Set<UserRole>([
  'ROLE_ADMIN',
  'ROLE_GESTOR',
  'ROLE_PARTICIPANTE',
]);

export class AuthMapper{
  static toDomain(dto: LoginResponseDto): AuthSession{
    const roles = dto.roles.filter((role): role is UserRole => VALID_ROLES.has(role));

    return{
      username: dto.username,
      token: dto.token,
      roles,
      message: dto.message,
    };
  }
}