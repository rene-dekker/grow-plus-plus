package com.dekkerr.gpp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dekkerr.gpp.dao.HasId;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Customer implements HasId, Serializable {

  private static final long serialVersionUID = -8925876116729942010L;

  @Id
  private String id;
  private String firstName;
  private String classification;
  private String surname;
  private List<Account> accounts;
  private String email;
  private Date changeDate = new Date();
  private List<Transaction> transactions = new ArrayList<Transaction>();
  private String city;
  private String country;

  /**
   * @return the id
   */
  @Override
  public final String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  @Override
  public final void setId(final String id) {
    this.id = id;
  }

  /**
   * @return the firstName
   */
  public final String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public final void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the surname
   */
  public final String getSurname() {
    return surname;
  }

  /**
   * @param surname
   *          the surname to set
   */
  public final void setSurname(final String surname) {
    this.surname = surname;
  }

  /**
   * @return the accounts
   */
  public final List<Account> getAccounts() {
    return accounts;
  }

  /**
   * @param accounts
   *          the accounts to set
   */
  public final void setAccounts(final List<Account> accounts) {
    this.accounts = accounts;
  }

  /**
   * @return the email
   */
  public final String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public final void setEmail(final String email) {
    this.email = email;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(final List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Date getChangeDate() {
    return changeDate;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(final String classification) {
    this.classification = classification;
  }

  public void setChangeDate(final Date changeDate) {
    this.changeDate = changeDate;
  }

  /**
   * @return the city
   */
  public final String getCity() {
    return city;
  }

  /**
   * @param city
   *          the city to set
   */
  public final void setCity(final String city) {
    this.city = city;
  }

  /**
   * @return the country
   */
  public final String getCountry() {
    return country;
  }

  /**
   * @param country
   *          the country to set
   */
  public final void setCountry(final String country) {
    this.country = country;
  }

}
