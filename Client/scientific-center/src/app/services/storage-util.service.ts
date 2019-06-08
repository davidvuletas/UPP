import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageUtilService {

  static getCurrentTask() {
    return localStorage.getItem('taskId');
  }

  static setCurrentTask(taskId) {
    localStorage.setItem('taskId', taskId);
  }

  static setLoggedUser(username) {
    localStorage.setItem('loggedUser', username);
  }

  static getLoggedUser() {
    return localStorage.getItem('loggedUser');
  }

  static setUserRole(role) {
    localStorage.setItem('role', role);
  }

  static getUserRole() {
    return localStorage.getItem('role');
  }

  static setProcessId(processId) {
    localStorage.setItem('processId', processId);
  }

  static getProcessId() {
    return localStorage.getItem('processId');
  }


  constructor() {
  }
}
