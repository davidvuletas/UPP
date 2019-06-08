import {Component, OnInit} from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ProcessService} from '../../services/process.service';
import {take} from 'rxjs/operators';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  constructor(private processService: ProcessService) {
  }

  tasks;
  role;
  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.processService.getTaskByAssignee(StorageUtilService.getLoggedUser()).subscribe((tasks: []) => {
      this.tasks = tasks;
      this.checkTask(this.tasks);
    });
  }

  private checkTask(tasks: any) {
    for (const task of tasks) {
      console.log(task);
    }
  }

}
