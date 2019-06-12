import {Component, OnInit} from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ProcessService} from '../../services/process.service';
import {JournalService} from '../../services/journal.service';
import saveAs from 'file-saver';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {ReviewerService} from '../../services/reviewer.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  validationPassed = false;

  constructor(private processService: ProcessService, private journalService: JournalService,
              private toast: ToastrService, private router: Router) {
  }

  role;
  papers: [];
  rejectionMessage: any;

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.processService.getTaskByAssignee(StorageUtilService.getLoggedUser()).subscribe((tasks: []) => {
      this.checkTask(tasks);
    });
  }

  private checkTask(tasks: any) {
    for (const task of tasks) {
      console.log(task);
      if (task['taskName'] === 'Select scientific paper for editing and do validation for format') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.journalService.getAllPapersForEditor().subscribe((papers: []) => {
          this.papers = papers;
        });
      } else if (task['taskName'] === 'Check PDF format') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.journalService.getAllPapersForEditor().subscribe((papers: []) => {
          this.papers = papers;
          this.validationPassed = true;
        });
      } else if (task['taskName'] === 'Choose reviewers') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.router.navigateByUrl('/reviewers');
      } else if (task['taskName'] === 'Submit review of paper') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.router.navigateByUrl('/review');
      } else if (task['taskName'] === 'Editor make decision') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.router.navigateByUrl('/reviews/editor-decision');
      } else if (task['taskName'] === 'Submit changes and answer on questions') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.router.navigateByUrl('/review/changes');
      } else if (task['taskName'] === 'Submit changes for existing paper') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.router.navigateByUrl('/changes');
      } else if (task['taskName'] === 'Payment') {
        StorageUtilService.setProcessId(task['processInstanceId']);
        this.router.navigateByUrl('/payment');
      }
    }
  }

  downloadPaper(name: string) {
    this.journalService.doValidationFormat(name, 'true').subscribe(processId => {
      StorageUtilService.setProcessId(processId);
      this.journalService.downloadPaper(name).subscribe(data => {
          const blob = new Blob([data], {type: 'application/pdf;charset=utf-8'});
          saveAs(blob, 'paper.pdf');
          this.validationPassed = true;
        }
      );
    });

  }

  acceptPaper(name: any) {
    this.journalService.pdfFormatValidation(name, 'true', '').subscribe(processId => {
      StorageUtilService.setProcessId(processId);
      this.toast.success('You have been successfully accept paper', 'Accept');
      this.router.navigateByUrl('/tasks');
    });
  }

  rejectPaper(name: any) {
    if (this.validationPassed === false) {
      this.journalService.doValidationFormat(name, 'false').subscribe(processId => {
        StorageUtilService.setProcessId(processId);
        this.toast.success('You have been successfully reject paper', 'Reject');
        window.location.reload();
      });
    } else {
      this.journalService.pdfFormatValidation(name, 'false', this.rejectionMessage).subscribe(processId => {
        StorageUtilService.setProcessId(processId);
        this.toast.success('You have been successfully reject paper,' +
          '\n author will be notified for changes that need to do', 'Reject');
        window.location.reload();
      });
    }
  }


}
