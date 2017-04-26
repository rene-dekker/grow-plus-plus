package com.dekkerr.gpp.model;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author dekkerr
 * 
 */
@Entity
public class Transaction implements Serializable {

  private static final long serialVersionUID = -399545807575193215L;

  @Id
  private String id;
  private String type;
  private Date date;
  private Double amount;

  /**
   * @return the id
   */
  public final String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public final void setId(final String id) {
    this.id = id;
  }

  /**
   * @return the date
   */
  public final Date getDate() {
    return date;
  }

  /**
   * @param date
   *          the date to set
   */
  public final void setDate(final Date date) {
    this.date = date;
  }

  /**
   * @return the amount
   */
  public final Double getAmount() {
    return amount;
  }

  /**
   * @param amount
   *          the amount to set
   */
  public final void setAmount(final Double amount) {
    this.amount = amount;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

}
