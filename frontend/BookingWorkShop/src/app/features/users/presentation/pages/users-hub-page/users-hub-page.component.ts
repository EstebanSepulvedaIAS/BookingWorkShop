import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";

@Component({
    selector: 'app-users-hub-page',
    standalone: true,
    imports: [RouterLink],
    templateUrl: './users-hub-page.component.html',
    styleUrl: './users-hub-page.component.css'
})
export class UsersHubPageComponent {}