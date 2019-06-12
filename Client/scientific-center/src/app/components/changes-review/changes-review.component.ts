import {Component, OnInit} from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ReviewService} from '../../services/review.service';
import {JournalService} from '../../services/journal.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

@Component({
  selector: 'app-changes-review',
  templateUrl: './changes-review.component.html',
  styleUrls: ['./changes-review.component.css']
})
export class ChangesReviewComponent implements OnInit {

  role;
  reviews = [];
  paperTitle;
  paperPDF;

  constructor(private reviewService: ReviewService, private journalService: JournalService,
              private toastService: ToastrService, private router: Router) {
  }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.reviewService.getAllReviewsForPaper().subscribe(reviewsWithTitle => {
      this.reviews = reviewsWithTitle['reviews'];
      this.paperTitle = reviewsWithTitle['paperName'];
    });
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
