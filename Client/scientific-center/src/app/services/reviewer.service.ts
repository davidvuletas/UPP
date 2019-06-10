import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {URIConstants} from './uri_constants';
import {StorageUtilService} from './storage-util.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewerService {


  constructor(private http: HttpClient) {
  }

  getAllPotentialReviewers() {
    return this.http.get(URIConstants.REVIEWERS_URL.concat(StorageUtilService.getProcessId()));
  }

  chooseReviewers(choosenReviewers) {
    return this.http.post(URIConstants.REVIEWERS_URL.concat(StorageUtilService.getProcessId()), choosenReviewers);
  }
}
