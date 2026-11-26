import { EnvironmentProviders, makeEnvironmentProviders } from "@angular/core";
import { UserRepository } from "../domain/repositories/user.repository";
import { UserHttpRepository } from "./repositories/user-http.repository";

export function provideUsers(): EnvironmentProviders {
    return makeEnvironmentProviders([
        { provide: UserRepository, useClass: UserHttpRepository },
    ]);
}