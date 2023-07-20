package xyz.k4czp3r.crafterinfo.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton utility class.
 */
public class SingletonUtils {

  private static SingletonUtils instance;
  private final Map<Class<?>, Object> instances;

  private SingletonUtils() {
    this.instances = new HashMap<>();
  }

  /**
   * Returns instance of SingletonUtils.
   *
   * @return SingletonUtils instance.
   */
  public static synchronized SingletonUtils getInstance() {
    if (instance == null) {
      instance = new SingletonUtils();
    }
    return instance;
  }

  /**
   * Returns instance of T class.
   *
   * @param c   Class to get instance of.
   * @param <T> Class to get instance of.
   * @return Instance of T class.
   * @throws Exception Throws when instance cannot be created.
   */
  public <T> T getInstance(Class<T> c)
      throws RuntimeException {
    synchronized (this) {
      if (!instances.containsKey(c)) {
        try {
          instances.put(c, c.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                 | IllegalAccessException e) {
          throw new RuntimeException("Failed to create instance of " + c.getName(), e);
        }

      }
      return c.cast(instances.get(c));
    }
  }

  /**
   * Register instance of T class.
   *
   * @param instance Instance to register.
   * @param <T>      Instance to register.
   */
  public <T> void registerInstance(T instance) {
    synchronized (this) {
      instances.put(instance.getClass(), instance);
    }
  }


}
