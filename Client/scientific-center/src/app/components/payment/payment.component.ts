import { Component, OnInit } from '@angular/core';
import {JournalService} from '../../services/journal.service';
import {StorageUtilService} from '../../services/storage-util.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  constructor(private journalService: JournalService, private toastService: ToastrService, private router: Router) { }

  ngOnInit() {
  }

  payment(success: string) {
    this.journalService.simulateTransaction(success).subscribe((processId) => {
      StorageUtilService.setProcessId(processId);
      this.toastService.success('Successfully completed payment transaction!', 'Success');
      this.router.navigateByUrl('/journals/'.concat(this.router.url.split('/')[2], '/upload-paper'));
    }, error1 =>  {
      this.toastService.error('Unsuccessfully payment transaction', 'Error');
    });
  }
}
