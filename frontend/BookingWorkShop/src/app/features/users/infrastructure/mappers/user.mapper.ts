import { User } from "../../domain/models/user.model";
import { UserResponseDto } from "../dtos/user-response.dto";

export class UserMapper {
    static toDomain(dto: UserResponseDto): User {
        return {
            id: dto.id,
            name: dto.name,
            email: dto.email,
        };
    }

    static toDomainList(dtos: UserResponseDto[]): User[] {
        return dtos.map((dto) => UserMapper.toDomain(dto));
    }
}