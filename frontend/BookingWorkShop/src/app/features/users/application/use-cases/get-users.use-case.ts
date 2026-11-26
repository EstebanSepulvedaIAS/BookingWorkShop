import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../../domain/models/user.model";
import { UserRepository } from "../../domain/repositories/user.repository";
import { readonly } from "@angular/forms/signals";

@Injectable({ providedIn: 'root' })
export class GetUsersUseCase {
    private readonly userRepositoy = inject(UserRepository);
    
    execute(): Observable<User[]> {
        return this.userRepositoy.getAll();
    }
}