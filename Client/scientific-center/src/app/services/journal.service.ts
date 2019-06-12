import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {URIConstants} from './uri_constants';
import {StorageUtilService} from './storage-util.service';

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
    const f = new FormData();
    f.append('file', file);
    return this.http.post(URIConstants.JOURNALS_URL.concat('/', journalId, '/paper/', paperName, '/upload/', processId), f);
  }

  getDataForJournal(processId) {
    return this.http.get(URIConstants.UPLOAD_PAPER_INFO_URL.concat(processId));
  }

  getAllPapersForEditor() {
    return this.http.get(URIConstants.JOURNALS_URL.concat('/', StorageUtilService.getLoggedUser()));
  }

  doValidationFormat(name, value) {
    const paper = [{
      fieldId: 'valid',
      fieldValue: value
    }, {
      fieldId: 'name',
      fieldValue: name
    }];
    return this.http.post(URIConstants.JOURNALS_PAPER_FORMAT_VALIDATION
      .concat(StorageUtilService.getProcessId()), paper, {responseType: 'text'});
  }

  getPaper() {
    return this.http.get(URIConstants.JOURNALS_URL.concat('/paper/', StorageUtilService.getProcessId(), '/object'));
  }

  downloadPaper(paperName) {
    return this.http.get(URIConstants.JOURNALS_URL.concat('/paper/', paperName), {responseType: 'blob'});
  }

  getJournalByPaperId(id) {
    return this.http.get(URIConstants.JOURNALS_URL.concat('/journal/paper/', id));
  }

  simulateTransaction(state) {
    return this.http.get(URIConstants.USER_URL.concat('payment/', state, '/', StorageUtilService.getProcessId()), {responseType: 'text'});
  }

  pdfFormatValidation(paperName, valid, comment) {
    const formatValid = [{
      fieldId: 'format',
      fieldValue: valid
    }, {
      fieldId: 'comment',
      fieldValue: comment
    }];
    return this.http.post(URIConstants.JOURNALS_PAPER_PDF_FORMAT_VALIDATION.concat(StorageUtilService.getProcessId())
      , formatValid, {responseType: 'text'});
  }
}
