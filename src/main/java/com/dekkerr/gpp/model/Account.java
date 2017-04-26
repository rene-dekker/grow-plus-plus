package com.dekkerr.gpp.model;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author dekkerr
 * 
 */
@Entity
public class Account implements Serializable {

  private static final long serialVersionUID = -1003710258152484148L;

  @Id
  private String id;

  private List<Transaction> transactions;

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(final List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }
}
