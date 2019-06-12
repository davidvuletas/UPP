import {Component, OnInit} from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ReviewService} from '../../services/review.service';
import {ToastrService} from 'ngx-toastr';
import {JournalService} from '../../services/journal.service';
import saveAs from 'file-saver';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  role;
  review = {};
  private suggestionForAccepting;
  private commentForEditor;
  private commentForAuthor;
  private title;
  suggestions = {
    ACCEPT: 'Accept',
    ACCEPT_WITH_MINOR_CHANGES: 'Accept with minor changes',
    ACCEPT_WITH_BIG_CHANGES: 'Accept with big changes',
    REJECT: 'Reject'
  };

  constructor(private reviewService: ReviewService, private toast: ToastrService, private journalService: JournalService) {
  }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.journalService.getPaper().subscribe(paper => {
      this.title = paper['title'];
    });
  }

  addReview() {
    this.review = {
      suggestionForAccepting: this.suggestionForAccepting,
      commentForEditor: this.commentForEditor,
      commentForAuthor: this.commentForAuthor,
      username: StorageUtilService.getLoggedUser()
    };
    this.reviewService.sendReview(this.review).subscribe(processId => {
      this.toast.success('Your review is successfully send!', 'Success');
    });
  }

  downloadPaper() {
    this.journalService.downloadPaper(this.title).subscribe(data => {
        const blob = new Blob([data], {type: 'application/pdf;charset=utf-8'});
        saveAs(blob, 'paper.pdf');
      }
    );
  }
}
