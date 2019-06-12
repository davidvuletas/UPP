import {Component, OnInit} from '@angular/core';
import {JournalService} from '../../services/journal.service';
import {StorageUtilService} from '../../services/storage-util.service';
import {ProcessService} from '../../services/process.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-journals',
  templateUrl: './journals.component.html',
  styleUrls: ['./journals.component.css']
})
export class JournalsComponent implements OnInit {

  private journals = [];
  role;

  constructor(private journalService: JournalService, private processService: ProcessService, private router: Router) {
  }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.journalService.getAllJournals().subscribe((journals: []) => {
      this.journals = journals;
    });
  }


  onJournalClicked(name, id) {
    this.processService.startProcess('main').subscribe(form => {
      StorageUtilService.setProcessId(form['processInstanceId']);
      this.journalService.chooseJournal(name, id, form['processInstanceId']).subscribe(() => {
        this.processService.getTaskByProcessId(form['processInstanceId']).subscribe(task => {
          StorageUtilService.setCurrentTask(task['id']);
          if (task['formFields'].length === 0) {
            this.router.navigateByUrl('/'.concat(id, '/payment'));
          } else {
            this.router.navigateByUrl('journals/'.concat(id, '/upload-paper'));
          }
        });
      });
    });
  }
}
