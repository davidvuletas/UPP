import { Component, OnInit } from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ReviewService} from '../../services/review.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-editor-decision',
  templateUrl: './editor-decision.component.html',
  styleUrls: ['./editor-decision.component.css']
})
export class EditorDecisionComponent implements OnInit {

  role;
  reviews = [];
  paperTitle;
  constructor(private reviewService: ReviewService, private toastService: ToastrService) { }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.reviewService.getAllReviewsForPaper().subscribe(reviewsWithTitle => {
      this.reviews = reviewsWithTitle['reviews'];
      this.paperTitle = reviewsWithTitle['paperName'];
    });
  }


  decideForPaper(status: string) {
    const body = {changes: status};
    this.reviewService.sendDecision(body).subscribe(processId => {
      StorageUtilService.setProcessId(processId);
      this.toastService.success('Your decision is submitted', 'Success');
    });
  }
}
