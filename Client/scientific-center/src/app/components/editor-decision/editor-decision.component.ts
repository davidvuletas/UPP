import { Component, OnInit } from '@angular/core';
import {StorageUtilService} from '../../services/storage-util.service';
import {ReviewService} from '../../services/review.service';

@Component({
  selector: 'app-editor-decision',
  templateUrl: './editor-decision.component.html',
  styleUrls: ['./editor-decision.component.css']
})
export class EditorDecisionComponent implements OnInit {

  role;
  reviews = [];
  paperTitle;
  constructor(private reviewService: ReviewService) { }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.reviewService.getAllReviewsForPaper().subscribe(reviewsWithTitle => {
      this.reviews = reviewsWithTitle['reviews'];
      this.paperTitle = reviewsWithTitle['paperName'];
    });
  }



}
