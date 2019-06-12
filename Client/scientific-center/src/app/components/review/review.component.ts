import {Component, OnInit} from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ReviewService} from '../../services/review.service';
import {ToastrService} from 'ngx-toastr';

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
  suggestions = {
    ACCEPT: 'Accept',
    ACCEPT_WITH_MINOR_CHANGES: 'Accept with minor changes',
    ACCEPT_WITH_BIG_CHANGES: 'Accept with big changes',
    REJECT: 'Reject'
  };

  constructor(private reviewService: ReviewService, private toast: ToastrService) {
  }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
  }

  addReview() {
    this.review = {
      suggestionForAccepting: this.suggestionForAccepting,
      commentForEditor: this.commentForEditor,
      commentForAuthor: this.commentForAuthor,
      username: StorageUtilService.getLoggedUser()
    };
    this.reviewService.sendReview(this.review).subscribe(processId => {
      StorageUtilService.setProcessId(processId);
      this.toast.success('Your review is successfully send!', 'Success');
    });
  }
}
