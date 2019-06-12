import {Component, OnInit} from '@angular/core';
import {JournalService} from '../../services/journal.service';
import {Router} from '@angular/router';
import {StorageUtilService} from '../../services/storage-util.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-changes',
  templateUrl: './changes.component.html',
  styleUrls: ['./changes.component.css']
})
export class ChangesComponent implements OnInit {

  paperTitle;
  paperPDF;
  pdfComment;

  constructor(private journalService: JournalService, private router: Router, private toastService: ToastrService) {
  }

  ngOnInit() {
    this.journalService.getPaper().subscribe(paper => {
      this.paperTitle = paper['title'];
      this.pdfComment = paper['pdfComment'];
    })
  }

  changePaper() {
    this.journalService.getJournalByPaperId(this.paperTitle).subscribe((journal) => {
      this.journalService.sendFile(journal['id'],
        this.paperTitle, StorageUtilService.getProcessId(), this.paperPDF).subscribe(() => {
        console.log('File uploaded');
        this.toastService.success('You have successfully upload changes, please wait for review.', 'Uploaded');
        this.router.navigate(['/homepage']);
      });
    });
  }

  onChange(event) {
    this.paperPDF = event.target.files.item(0);
  }

}
