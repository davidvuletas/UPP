import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ProcessService} from './process.service';
import {StorageUtilService} from './storage-util.service';
import {URIConstants} from './uri_constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }


  login(loginForm) {
    return this.http.post(URIConstants.LOGIN_URL.concat(StorageUtilService.getCurrentTask()), loginForm,
      {responseType: 'text'});
  }

  registration(userForRegistration) {
    return this.http.post(URIConstants.REGISTER_URL.concat(StorageUtilService.getCurrentTask()), userForRegistration,
      {responseType: 'text'});
  }

  getUserRole(username) {
    return this.http.get(URIConstants.USER_URL.concat(username, '/role'));
  }
}
