import { EnvironmentProviders, makeEnvironmentProviders } from "@angular/core";
import { AuthRepository } from "../domain/repositories/auth.reposiroty";
import { AuthHttpRepository } from "./repositories/auth-http.repository";
import { LocalTokenStorageRepository } from "./repositories/local-token-storage.repository";
import { TokenStorageRepository } from "../domain/repositories/token-storage.repository";

export function provideAuth(): EnvironmentProviders {
        return makeEnvironmentProviders([
            {provide: AuthRepository, useClass: AuthHttpRepository},
            {provide: TokenStorageRepository, useClass: LocalTokenStorageRepository},
        ]);
}