import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ReviewerService} from '../../services/reviewer.service';
import {StorageUtilService} from '../../services/storage-util.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-reviewer',
  templateUrl: './reviewer.component.html',
  styleUrls: ['./reviewer.component.css']
})
export class ReviewerComponent implements OnInit {

  constructor(private reviewerService: ReviewerService, private toast: ToastrService) {
  }

  reviewers = [];
  role;
  selectedReviewers = [];

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
    this.reviewerService.getAllPotentialReviewers().subscribe((reviewers: []) => {
      this.reviewers = reviewers;
    });
  }

  chooseReviewers(main: boolean) {
    this.reviewerService.chooseReviewers(this.selectedReviewers, main).subscribe(processId => {
      StorageUtilService.setProcessId(processId);
      this.toast.success('Reviewers are chosen, they will receive message to do review', 'Success');
    });
  }
}
