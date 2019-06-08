import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {ProcessService} from '../../services/process.service';
import {StorageUtilService} from '../../services/storage-util.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  public formFields;
  public welcomePage;
  public loginClicked;
  public registerClicked;

  constructor(private userService: UserService, private processService: ProcessService,
              private router: Router) {
  }

  ngOnInit() {
    this.processService.startProcess('user').subscribe(form => {
      StorageUtilService.setCurrentTask(form['taskId']);
    });
    this.welcomePage = true;
    this.loginClicked = false;
    this.registerClicked = false;
  }

  login(value, form) {
    let loginForm = [];
    loginForm = ProcessService.getFormValues(value, loginForm);
    this.userService.login(loginForm).subscribe(loggedUser => {
      StorageUtilService.setLoggedUser(loggedUser);
      this.userService.getUserRole(loggedUser).subscribe(role => {
        StorageUtilService.setUserRole(role);
        this.router.navigate(['homepage']);
      });
    });
  }

  register(value, from) {
    let registerForm = [];
    registerForm = ProcessService.getFormValues(value, registerForm);
    this.userService.registration(registerForm).subscribe((processId: string) => {
      this.processService.getTaskByProcessId(processId).subscribe(form => {
        this.router.navigate(['welcome']);
        StorageUtilService.setProcessId(processId);
        StorageUtilService.setCurrentTask(form['taskId']);
        this.onLoginClick();
        this.registerClicked = false;
      });
    });
    console.log('register');
  }

  onLoginClick() {
    this.processService.chooseLoginOrRegister(true).subscribe(processId => {
      console.log(processId);
      StorageUtilService.setProcessId(processId);
      this.processService.getTaskByProcessId(processId).subscribe(form => {
        this.formFields = form['formFields'];
        StorageUtilService.setCurrentTask(form['taskId']);
        this.welcomePage = false;
        this.loginClicked = true;
      });
    });
  }

  onRegisterClick() {
    this.processService.chooseLoginOrRegister(false).subscribe(processId => {
      console.log(processId);
      this.processService.getTaskByProcessId(processId).subscribe(form => {
        console.log(form);
        this.formFields = form['formFields'];
        StorageUtilService.setCurrentTask(form['taskId']);
        this.welcomePage = false;
        this.registerClicked = true;
      });
    });
  }

  onBackClick() {
    this.registerClicked = false;
    this.loginClicked = false;
    this.welcomePage = true;
    this.formFields = [];
    this.router.navigate(['welcome']);
  }
}
