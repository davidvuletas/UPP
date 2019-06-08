import {Component, Input, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {StorageUtilService} from '../../services/storage-util.service';
import {ProcessService} from '../../services/process.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  @Input()
  role: string;
  clicked: boolean;
  constructor(private router: Router, private processService: ProcessService) { }

  ngOnInit() {
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl('/welcome');
  }

  uploadPaper() {
    this.clicked = true;
    this.processService.startProcess('main').subscribe(form => {
      StorageUtilService.setCurrentTask(form['taskId']);
      this.router.navigate(['journals']);
    });
  }

  getAllTasks() {
    this.router.navigate(['/tasks']);
  }
}
