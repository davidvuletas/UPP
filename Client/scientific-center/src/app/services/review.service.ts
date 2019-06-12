import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {URIConstants} from './uri_constants';
import {StorageUtilService} from './storage-util.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) {
  }

  sendReview(review) {
    return this.http.post(URIConstants.REVIEW_URL.concat(StorageUtilService.getProcessId()), review, {responseType: 'text'});
  }

  getAllReviewsForPaper() {
    return this.http.get(URIConstants.REVIEW_URL.concat('all/').concat(StorageUtilService.getProcessId()));
  }

  sendDecision(decision) {
    return this.http.post(URIConstants.REVIEW_URL.concat('/decision/', StorageUtilService.getProcessId()), decision,
      {responseType: 'text'});
  }
}
