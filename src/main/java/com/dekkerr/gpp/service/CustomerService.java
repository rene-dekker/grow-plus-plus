package com.dekkerr.gpp.service;

import java.io.IOException;
import java.util.List;

import com.dekkerr.gpp.api.CustomerClassifier;
import com.dekkerr.gpp.dao.CustomerBigqueryDao;
import com.dekkerr.gpp.dao.CustomerDataStoreDao;
import com.dekkerr.gpp.model.Customer;
import com.dekkerr.gpp.model.Transaction;
import com.dekkerr.gpp.model.TransactionList;
import com.google.api.server.spi.response.NotFoundException;

/**
 * @author dekkerr
 * 
 */
public class CustomerService {

  private final CustomerDataStoreDao dsDao = new CustomerDataStoreDao();

  private final CustomerBigqueryDao bqDao = new CustomerBigqueryDao();

  public Customer insert(final Customer customer) throws NotFoundException {
    customer.setId(CustomerDataStoreDao.generateId(Customer.class));
    customer.setClassification(CustomerClassifier.classify(customer
        .getTransactions()));
    dsDao.insert(customer);
    try {
      // TODO make this async for obvious reasons.
      bqDao.insert(customer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return get(customer.getId());
  }

  public Customer update(final Customer customer) throws NotFoundException {
    customer.setClassification(CustomerClassifier.classify(customer
        .getTransactions()));
    dsDao.update(customer);
    try {
      // TODO make this async for obvious reasons.
      bqDao.insert(customer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return get(customer.getId());
  }

  public void delete(final Customer customer) {
    dsDao.delete(customer);
  }

  public void delete(final String customerId) {
    Customer customer = new Customer();
    customer.setId(customerId);
    dsDao.delete(customer);
  }

  public List<Customer> list() {
    return dsDao.list(0, 99999);
  }

  public Customer get(final String id) throws NotFoundException {
    Customer ret = dsDao.get(id);
    if (ret == null) {
      throw new NotFoundException("Not found: " + id);
    }
    return dsDao.get(id);
  }

  public TransactionList listTransactions(final String customerId)
      throws NotFoundException {
    TransactionList ret = new TransactionList();
    ret.setTransactions(get(customerId).getTransactions());
    return ret;
  }

  public void insertTransaction(final String customerId,
      final Transaction transaction) throws NotFoundException {
    Customer customer = get(customerId);
    customer.getTransactions().add(transaction);
    update(customer);
  }

  public String getClassification(final String customerId)
      throws NotFoundException {
    Customer c = get(customerId);
    return CustomerClassifier.classify(c.getTransactions());
  }
}
