import { Component, OnInit } from '@angular/core';
import { Transaction } from 'src/app/shared/model/transaction';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-transactions-page',
  templateUrl: './transactions-page.component.html',
  styleUrls: ['./transactions-page.component.css']
})
export class TransactionsPageComponent implements OnInit {
  transactions: Transaction[];
  private relativeUrlForAllTransactions = '/payment/transactions';


  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.transactions = [];
  }

  ngOnInit() {
    this.getAllTransactions();
  }

  getAllTransactions() {
    this.genericService.get<Transaction[]>(this.relativeUrlForAllTransactions).subscribe(
      (transactions: Transaction[]) => {
        this.transactions = transactions;
        this.toastr.success('Transactions loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading transactions!');
      }
    );
  }
}
