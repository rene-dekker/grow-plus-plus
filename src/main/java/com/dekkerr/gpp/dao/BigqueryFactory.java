package com.dekkerr.gpp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Builder;
import com.google.appengine.api.utils.SystemProperty;

/**
 * @author dekkerr
 * 
 */
public final class BigqueryFactory {

  private BigqueryFactory() {
  }

  private static final Logger LOGGER = Logger.getLogger(BigqueryFactory.class);

  private static Bigquery bigquery;

  private static final String[] SCOPES = new String[] { "https://www.googleapis.com/auth/bigquery" };

  public static Bigquery getBigquery() {
    if (bigquery == null) {
      bigquery = initBQ();
    }
    return bigquery;
  }

  public static Bigquery initBQ() {
    Bigquery ret = null;
    try {
      HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
      HttpRequestInitializer initializer = getCredential(transport);
      Builder builder = new Bigquery.Builder(transport, new JacksonFactory(),
          initializer);
      builder.setApplicationName(AbstractBigqueryDao.PROJECT_ID);
      ret = builder.build();
    } catch (IOException | GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
    return ret;
  }

  /**
   * @param transport
   *          the HttpTransport to use.
   * @return a HttpRequestInitializer based on a service account.
   */
  public static final Credential getCredential(final HttpTransport transport) {
    Credential credential = null;
    try {
      if (SystemProperty.Environment.Value.Production
          .equals(SystemProperty.environment.value())) {
        credential = GoogleCredential.getApplicationDefault().createScoped(
            Arrays.asList(SCOPES));
      } else {
        credential = GoogleCredential.fromStream(
            getInputStream("serviceaccountkey.json")).createScoped(
            Arrays.asList(SCOPES));
      }
    } catch (IOException e) {
      LOGGER.error(e);
    }
    return credential;
  }

  @SuppressWarnings("resource")
  private static InputStream getInputStream(final String fileName) {
    File file = new File(fileName);
    InputStream is = null;
    try {
      is = new FileInputStream(file);
    } catch (Exception e) {
      if (is == null) {
        LOGGER.debug("Could not locate File " + fileName
            + ", trying to load from the classpath.");
      }
      is = BigqueryFactory.class.getResourceAsStream(fileName);
      if (is == null) {
        is = BigqueryFactory.class.getResourceAsStream("/" + fileName);
        if (is == null) {
          LOGGER.error("Could not locate File " + fileName + " the classpath.");
        }
      }
    }
    return is;
  }

}
