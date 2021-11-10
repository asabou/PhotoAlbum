import { Component } from '@angular/core';
import { environment } from 'src/environments/environment';

export const SERVER_URL = environment.serverUrl;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'photo-album-web';
}
