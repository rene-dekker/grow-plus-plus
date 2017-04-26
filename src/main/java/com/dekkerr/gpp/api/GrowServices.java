/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.dekkerr.gpp.api;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dekkerr.gpp.model.Classification;
import com.dekkerr.gpp.model.Customer;
import com.dekkerr.gpp.model.Transaction;
import com.dekkerr.gpp.model.TransactionList;
import com.dekkerr.gpp.service.CustomerService;
import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.gson.Gson;

/**
 * Grow services api.
 * 
 * @author dekkerr
 * 
 */
@Api(name = "growservices", version = "v1", namespace = @ApiNamespace(ownerDomain = "gpp.dekkerr.com", ownerName = "gpp.dekkerr.com", packagePath = ""), description = "Grow services api, for all your financial needs.")
public class GrowServices {

  private final Logger logger = Logger.getLogger(GrowServices.class);

  private final Gson gson = new Gson();

  private final CustomerService service = new CustomerService();

  @ApiMethod(name = "customers.insert", path = "customers/")
  public Customer insertCustomer(final Customer customer, final User user,
      final HttpServletRequest req) throws ServiceException {
    logger.info(String.format("POST customers/\n", gson.toJson(customer)));
    validateCustomer(customer);
    if (customer.getChangeDate() == null) {
      customer.setChangeDate(new Date());
    }
    return service.insert(customer);
  }

  private void validateCustomer(final Customer customer)
      throws ServiceException {
    if (customer.getFirstName() == null || customer.getEmail() == null
        || customer.getSurname() == null || customer.getCountry() == null
        || customer.getCity() == null) {
      throw new ServiceException(400,
          "Required fields: firstName, email, surname, country, city");
    }
  }

  private void validateTransaction(final Transaction txn)
      throws ServiceException {
    if (txn.getAmount() == null || txn.getType() == null || txn.getId() == null) {
      throw new ServiceException(400, "Required fields: amount, date, id");
    }
    CustomerClassifier.validate(txn.getType());
  }

  @ApiMethod(name = "customers.get", path = "customers/{customerId}")
  public final Customer get(@Named("customerId") final String customerId,
      final User user, final HttpServletRequest req) throws ServiceException {
    logger.info(String.format("GET customers%s", customerId));
    return service.get(customerId);
  }

  @ApiMethod(name = "customers.list", path = "customers")
  public final List<Customer> list(final User user, final HttpServletRequest req) {
    logger.info("GET customers");
    return service.list();
  }

  @ApiMethod(name = "customers.delete", path = "customers/{customerId}", httpMethod = "DELETE")
  public final void delete(@Named("customerId") final String customerId,
      final User user, final HttpServletRequest req) {
    logger.info(String.format("DELETE customers/%s", customerId));
    service.delete(customerId);
  }

  @ApiMethod(name = "customers.update", path = "customers/{customerId}", httpMethod = "PUT")
  public final Customer update(final Customer customer, final User user,
      final HttpServletRequest req) throws ServiceException {
    logger.info(String.format("PUT customers/\n", gson.toJson(customer)));
    return service.update(customer);
  }

  @ApiMethod(name = "customers.transactions.list", path = "customers/{customerId}/transactions", httpMethod = "GET")
  public final TransactionList listTransactions(
      @Named("customerId") final String customerId, final User user,
      final HttpServletRequest req) throws ServiceException {
    logger.info(String.format("GET customers/%s/transactions", customerId));
    return service.listTransactions(customerId);
  }

  @ApiMethod(name = "customers.transactions.insert", path = "customers/{customerId}/transactions", httpMethod = "POST")
  public final void insertTransaction(
      @Named("customerId") final String customerId,
      final Transaction transaction, final User user,
      final HttpServletRequest req) throws ServiceException {
    logger.info(String.format("POST customers/%s/transactions", customerId));
    validateTransaction(transaction);
    if (transaction.getDate() == null) {
      transaction.setDate(new Date());
    }
    service.insertTransaction(customerId, transaction);
  }

  @ApiMethod(name = "customers.classify", path = "customers/{customerId}/classification", httpMethod = "GET")
  public final Classification classify(
      @Named("customerId") final String customerId, final User user,
      final HttpServletRequest req) throws ServiceException {
    logger.info(String.format("GET customers/%s/classification", customerId));
    Classification ret = new Classification();
    ret.setClassification(service.getClassification(customerId));
    return ret;
  }
}
