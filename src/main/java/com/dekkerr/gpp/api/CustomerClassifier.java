package com.dekkerr.gpp.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dekkerr.gpp.model.Transaction;
import com.google.api.server.spi.ServiceException;
import com.google.common.base.Joiner;

public class CustomerClassifier {

  private static final List<String> GOOD;
  private static final List<String> BAD;
  private static final List<String> ALL = new ArrayList<>();

  static {
    GOOD = Arrays.asList("pet food", "flowers", "groceries", "candy");
    BAD = Arrays.asList("plutonium", "weapons", "cluster bombs");
    ALL.addAll(GOOD);
    ALL.addAll(BAD);
  }

  public static final void validate(final String type) throws ServiceException {
    if (!ALL.contains(type)) {
      throw new ServiceException(400,
          "Invalid transaction type. Valid types are: "
              + Joiner.on(" and ").join(ALL));
    }
  }

  public static final String classify(final List<Transaction> txns) {
    String ret = "Unknown";
    if (txns != null) {
      int bad = 0;
      int good = 0;
      for (Transaction txn : txns) {
        if (GOOD.contains(txn.getType())) {
          good++;
        } else if (BAD.contains(txn.getType())) {
          bad++;
        }
      }
      if (good > bad) {
        ret = "Law abiding citizen";
      } else if (bad > good) {
        ret = "Terrorist";
      } else {
        ret = "Unclear, need more transactions to classify.";
      }
    }
    return ret;
  }
}
