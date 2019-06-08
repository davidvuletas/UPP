import { Component, OnInit } from '@angular/core';
import {ProcessService} from '../../services/process.service';
import {StorageUtilService} from '../../services/storage-util.service';
import {JournalService} from '../../services/journal.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  journals = [];
  uploadPaper = false;
  role;
  constructor(private processService: ProcessService, private journalService: JournalService, private router: Router) { }

  ngOnInit() {
    this.role = StorageUtilService.getUserRole();
  }

}
