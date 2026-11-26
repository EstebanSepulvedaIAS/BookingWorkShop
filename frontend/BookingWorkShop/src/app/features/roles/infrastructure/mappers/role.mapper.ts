import { Role, RoleBadgeTheme } from "../../domain/models/role.model";
import { RoleResponseDto } from "../dtos/role-response.dto";

export class RoleMapper {
    static toDomain(dto: RoleResponseDto): Role {
        const normalizedName = dto.name.trim().toUpperCase().replace(/^ROLE_/, '');

        return{
            id: dto.id,
            name: normalizedName,
            description: RoleMapper.resolveDescription(normalizedName),
        };
    }

    static toDomainList(dtos: RoleResponseDto[]): Role[] {
        return dtos.map((dto) => RoleMapper.toDomain(dto));
    }

    private static resolveDescription(normalizedName: string): string {
        switch (normalizedName){
            case 'ADMIN':
                return 'Acceso completo y gestión de la plataforma';
            case 'GESTOR':
                return 'Puede crear y modificar contenido';
            default:
                return 'Acceso a funciones básicas de la plataforma';
        }
    } 
}