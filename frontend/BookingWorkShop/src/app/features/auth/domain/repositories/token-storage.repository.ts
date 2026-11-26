export abstract class TokenStorageRepository{
    abstract saveToken(token: string): void;
    abstract getToken(): string | null;
    abstract clearToken(): void;
}