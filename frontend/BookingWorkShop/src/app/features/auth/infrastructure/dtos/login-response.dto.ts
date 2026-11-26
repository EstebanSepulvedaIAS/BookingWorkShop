export interface LoginResponseDto{
    username: string;
    message: string;
    token: string;
    roles: string[];
}