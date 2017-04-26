package com.dekkerr.gpp.dao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.Table;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.gson.Gson;

public abstract class AbstractBigqueryDao<T> {

  private static final String DATASET_ID = "growplusplus";

  public static final String PROJECT_ID = "gae-example-rene";

  private final String table;

  private final Bigquery bigquery = BigqueryFactory.getBigquery();

  public AbstractBigqueryDao(final String newTable) {
    table = newTable;
  }

  private final Logger logger = Logger.getLogger(AbstractBigqueryDao.class);

  public final void insert(final T obj) throws IOException {
    insert(obj, false);
  }

  public final void insert(final T obj, final boolean created)
      throws IOException {
    try {
      Gson gson = new Gson();
      logger.info("inserting into gbq: " + gson.toJson(obj));
      Rows rows = new Rows();
      rows.setJson(getJson(obj));
      List<Rows> rowsList = Arrays.asList(rows);
      TableDataInsertAllRequest req = new TableDataInsertAllRequest()
          .setRows(rowsList);
      bigquery.tabledata().insertAll(PROJECT_ID, DATASET_ID, table, req)
          .execute();
    } catch (Exception e) {
      logger.error(e);
      createTable();
      if (!created) {
        insert(obj, true);
      }
    }
  }

  public abstract Map<String, Object> getJson(T obj);

  public final void createTable() throws IOException {
    try {
      logger.info("Creating table " + table);
      TableSchema schema = getTableSchema();
      Table result = new Table();
      TableReference ref = new TableReference();
      ref.setDatasetId(DATASET_ID);
      ref.setProjectId(PROJECT_ID);
      ref.setTableId(table);
      result.setTableReference(ref);
      result.setSchema(schema);
      BigqueryFactory.getBigquery().tables()
          .insert(PROJECT_ID, DATASET_ID, result).execute();
    } catch (GoogleJsonResponseException e) {
      // CONFLICT 409 means table exists.
      if (e.getStatusCode() == HttpURLConnection.HTTP_CONFLICT) {
        logger.warn("Table " + table + " exists", e);
      } else {
        throw e;
      }
    }
  }

  public abstract TableSchema getTableSchema();
}
