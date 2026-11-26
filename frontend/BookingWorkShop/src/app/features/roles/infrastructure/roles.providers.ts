import { EnvironmentProviders, makeEnvironmentProviders } from "@angular/core";
import { RoleRepository } from "../domain/repositories/role.repository";
import { RoleHttpRepository } from "./repositories/role-http.repository";

export function provideRoles(): EnvironmentProviders{
    return makeEnvironmentProviders([
        { provide: RoleRepository, useClass: RoleHttpRepository },
    ]);
}