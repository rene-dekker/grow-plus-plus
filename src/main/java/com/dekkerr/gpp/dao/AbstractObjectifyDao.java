package com.dekkerr.gpp.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

/**
 * @author dekkerr
 * 
 * @param <T>
 *          the type
 */
public class AbstractObjectifyDao<T extends HasId> {

  private final Class<T> clazz;

  private final boolean cache = true;

  private final Logger logger = Logger.getLogger(AbstractObjectifyDao.class);

  /**
   * @param newClazz
   *          the managed entity class.
   */
  public AbstractObjectifyDao(final Class<T> newClazz) {
    clazz = newClazz;
    register();
  }

  private void register() {
    logger.info("Registering class " + clazz.getName() + " for objectify.");
    factory().register(clazz);
  }

  public final T get(final String key) {
    Key<T> dsKey = Key.<T> create(clazz, key);
    T ret = ofy().cache(cache).load().key(dsKey).now();
    return ret;
  }

  public final List<T> list(final long start, final long num) {
    Query<T> qry = ofy().cache(cache).load().type(clazz).offset((int) start);
    if (num >= 0) {
      qry = qry.limit((int) num);
    }
    return qry.list();
  }

  public final void insert(final T object) {
    ofy().save().entity(object).now();
  }

  public final void update(final T object) {
    ofy().cache(cache).save().entity(object).now();
  }

  public final void delete(final T object) {
    ofy().cache(cache).delete().entity(object).now();
  }

  /**
   * @return an ObjectifyService instance.
   */
  public static Objectify ofy() {
    return ObjectifyService.ofy();
  }

  /**
   * @return an ObjectifyFactory instance.
   */
  public static ObjectifyFactory factory() {
    return ObjectifyService.factory();
  }

  public static String generateId(final Class<?> clazz) {
    return ObjectifyService.factory().allocateId(clazz).getString();
  }
}
