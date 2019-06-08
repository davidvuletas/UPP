import {Component, OnInit} from '@angular/core';
import {JournalService} from '../../services/journal.service';
import {StorageUtilService} from '../../services/storage-util.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-upload-paper',
  templateUrl: './upload-paper.component.html',
  styleUrls: ['./upload-paper.component.css']
})
export class UploadPaperComponent implements OnInit {

  constructor(private journalService: JournalService, private router: Router, private toastrService: ToastrService) {
  }
  role;
  scientificAreas: [];
  allAuthors: [];
  authors = [{name: '', id: '', city: '', country: '', email: '', primary: false}];
  nums = [0];
  paperPDF;
  paper = {title: '', abstractOfPaper: '', keywords: '', scientificArea: '', coauthors: this.authors};

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.journalService.getDataForJournal(StorageUtilService.getProcessId()).subscribe(response => {
      this.scientificAreas = response['codeBooks'];
      this.allAuthors = response['authors'];
      console.log(this.allAuthors);
    });
  }

  uploadPaper() {
    console.log(this.paper);
    for (const user of this.allAuthors) {
      if ((user['email'] as string).split('@')[0] === StorageUtilService.getLoggedUser()) {
        this.authors.push({
          name: user['name'] + ' ' + user['lastname'],
          id: user['id'],
          city: user['city'],
          country: user['country'],
          email: user['email'],
          primary: true
        });
      }
    }
    this.paper.coauthors = this.authors;
    this.journalService.uploadPaper(this.router.url.split('/')[2], StorageUtilService.getProcessId(), this.paper)
      .subscribe(() => {
        this.journalService.sendFile(this.router.url.split('/')[2], this.paper.title,
          StorageUtilService.getProcessId(), this.paperPDF).subscribe(() => {
            console.log('File uploaded');
            this.toastrService.success('You have successfully upload paper, please wait for review.', 'Uploaded');
            this.router.navigate(['/homepage']);
        });
      });
  }

  newAuthor() {
    this.nums.push(0);
  }

  removeAuthor() {
    this.nums.splice(this.nums.length - 1, 1);
  }

  onChange(event) {
    this.paperPDF = event.target.files.item(0);
  }
}
