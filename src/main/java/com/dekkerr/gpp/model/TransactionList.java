package com.dekkerr.gpp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionList implements Serializable {

  private static final long serialVersionUID = -1187331725967178886L;

  private List<Transaction> transactions = new ArrayList<Transaction>();

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public final void setTransactions(final List<Transaction> transactions) {
    this.transactions = transactions;
  }
}
