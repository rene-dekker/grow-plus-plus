package com.dekkerr.gpp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dekkerr.gpp.model.Customer;
import com.dekkerr.gpp.model.Transaction;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

public class CustomerBigqueryDao extends AbstractBigqueryDao<Customer> {

  public CustomerBigqueryDao() {
    super("customer");
  }

  private List<TableFieldSchema> getTransactionSchema() {
    List<TableFieldSchema> transactions = new ArrayList<>();
    transactions.add(new TableFieldSchema().setName("id").setType("STRING"));
    transactions.add(new TableFieldSchema().setName("type").setType("STRING"));
    transactions.add(new TableFieldSchema().setName("date")
        .setType("TIMESTAMP"));
    transactions.add(new TableFieldSchema().setName("amount").setType("FLOAT"));
    return transactions;
  }

  @Override
  public final Map<String, Object> getJson(final Customer obj) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", obj.getId());
    map.put("firstName", obj.getFirstName());
    map.put("surname", obj.getSurname());
    map.put("email", obj.getEmail());
    map.put("transactions", getTransactionJson(obj.getTransactions()));
    map.put("changeDate", obj.getChangeDate().getTime() / 1000.);
    map.put("classification", obj.getClassification());
    map.put("country", obj.getCountry());
    map.put("city", obj.getCity());
    return map;
  }

  private Object getTransactionJson(final List<Transaction> txns) {
    List<Object> ret = new ArrayList<>();
    for (Transaction txn : txns) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("id", txn.getId());
      map.put("type", txn.getType());
      map.put("amount", txn.getAmount());
      map.put("date", txn.getDate().getTime() / 1000.);
      ret.add(map);
    }
    return ret;
  }

  @Override
  public final TableSchema getTableSchema() {
    List<TableFieldSchema> fields = new ArrayList<>();
    fields.add(new TableFieldSchema().setName("id").setType("STRING"));
    fields.add(new TableFieldSchema().setName("country").setType("STRING"));
    fields.add(new TableFieldSchema().setName("city").setType("STRING"));
    fields.add(new TableFieldSchema().setName("firstName").setType("STRING"));
    fields.add(new TableFieldSchema().setName("surname").setType("STRING"));
    fields.add(new TableFieldSchema().setName("email").setType("STRING"));
    fields.add(new TableFieldSchema().setName("transactions").setType("RECORD")
        .setFields(getTransactionSchema()).setMode("REPEATED"));
    fields.add(new TableFieldSchema().setName("changeDate")
        .setType("TIMESTAMP"));
    fields.add(new TableFieldSchema().setName("classification").setType(
        "STRING"));
    TableSchema schema = new TableSchema().setFields(fields);
    return schema;
  }
}
