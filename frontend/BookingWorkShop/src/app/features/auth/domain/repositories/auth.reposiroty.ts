import { Observable } from "rxjs";
import { AuthCredentials } from "../models/auth-credentials.model";
import { AuthSession } from "../models/auth-session.model";

export abstract class AuthRepository{
    abstract login(credentials: AuthCredentials): Observable<AuthSession>;
}