package com.dekkerr.gpp.dao;

import com.dekkerr.gpp.model.Customer;

public class CustomerDataStoreDao extends AbstractObjectifyDao<Customer> {

  public CustomerDataStoreDao() {
    super(Customer.class);
  }

}
