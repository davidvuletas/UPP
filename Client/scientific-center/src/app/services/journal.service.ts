import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {URIConstants} from './uri_constants';

@Injectable({
  providedIn: 'root'
})
export class JournalService {


  constructor(private http: HttpClient) {
  }

  getAllJournals() {
    return this.http.get(URIConstants.JOURNALS_URL);
  }

  chooseJournal(name, id, processId) {
    const journal = [{
      fieldId: 'name',
      fieldValue: name
    },
      {
        fieldId: 'id',
        fieldValue: id
      }];
    return this.http.post(URIConstants.JOURNAL_CHOOSE_URL.concat(processId), journal);
  }

  uploadPaper(id, processId, paper) {
    return this.http.post(URIConstants.JOURNALS_URL.concat('/', id, '/paper'), paper, {responseType: 'text'});
  }

  sendFile(journalId, paperName, processId, file) {
    const  f = new FormData();
    f.append('file', file);
    return this.http.post(URIConstants.JOURNALS_URL.concat('/', journalId, '/paper/', paperName, '/upload/', processId), f);
  }

  getDataForJournal(processId) {
    return this.http.get(URIConstants.UPLOAD_PAPER_INFO_URL.concat(processId));
  }
}
