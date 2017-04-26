package com.dekkerr.gpp.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author dekkerr
 * 
 */
public class JULAppender extends AppenderSkeleton {
  @Override
  public void close() {
  }

  @Override
  public final boolean requiresLayout() {
    return true;
  }

  @Override
  protected final void append(final LoggingEvent event) {
    Level level = Level.INFO;
    switch (event.getLevel().toInt()) {
    case org.apache.log4j.Level.FATAL_INT:
    case org.apache.log4j.Level.ERROR_INT:
      level = Level.SEVERE;
      break;
    case org.apache.log4j.Level.WARN_INT:
      level = Level.WARNING;
      break;
    case org.apache.log4j.Level.INFO_INT:
      level = Level.INFO;
      break;
    case org.apache.log4j.Level.DEBUG_INT:
      level = Level.FINE;
      break;
    case org.apache.log4j.Level.TRACE_INT:
      level = Level.FINER;
      break;
    case org.apache.log4j.Level.ALL_INT:
      level = Level.ALL;
      break;
    case org.apache.log4j.Level.OFF_INT:
      level = Level.OFF;
      break;
    default:
      level = Level.INFO;
      break;
    }
    Throwable t = null;
    if (event.getThrowableInformation() != null) {
      t = event.getThrowableInformation().getThrowable();
    }
    LogRecord record = new LogRecord(level, event.getRenderedMessage());
    record.setThrown(t);
    record.setSourceClassName(event.getLocationInformation().getClassName());
    record.setSourceMethodName(event.getLocationInformation().getMethodName());
    Logger.getLogger(event.getLoggerName()).log(record);
  }
}
