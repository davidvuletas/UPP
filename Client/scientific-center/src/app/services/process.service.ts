import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {URIConstants} from './uri_constants';
import {StorageUtilService} from './storage-util.service';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {


  constructor(private http: HttpClient) {
  }

  static getFormValues(value, form) {
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      form.push({fieldId: property, fieldValue: value[property]});
    }
    return form;
  }

  startProcess(type) {
    if (type === 'main') {
      return this.http.get(URIConstants.START_MAIN_PROCESS_URL.concat(StorageUtilService.getLoggedUser()));
    }
    return this.http.get(URIConstants.START_USER_PROCESS_URL);
  }

  getTaskByProcessId(processId: string) {
    return this.http.get(URIConstants.TASK_BY_PROCESS_URL.concat(processId));
  }

  getTaskByAssignee(username: string) {
    return this.http.get(URIConstants.TASK_BY_USERNAME_ASSIGNEE_URL.concat(username));
  }

  chooseLoginOrRegister(login) {
    const formChosen = [{
      'fieldId': login === true ? 'login' : 'register',
      'fieldValue': true
    }];
    return this.http.post(URIConstants.FORM_CHOSEN_URL.concat(StorageUtilService.getCurrentTask()), formChosen, {
      responseType: 'text'
    });
  }



}
