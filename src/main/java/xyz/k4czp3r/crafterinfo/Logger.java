package xyz.k4czp3r.crafterinfo;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

/**
 * Logger class used to access Plugin's logger everywhere.
 */
public final class Logger {

  private static Logger instance;

  private final ComponentLogger logger;

  private Logger(ComponentLogger logger) {
    this.logger = logger;
  }

  public void info(String message) {
    this.logger.info(message);
  }

  public void error(String message) {
    this.logger.error(message);
  }


  /**
   * Returns instance of Logger.
   *
   * @param logger Plugin's logger.
   * @return Logger instance.
   */
  public static Logger getInstance(ComponentLogger logger) {
    if (instance == null) {
      instance = new Logger(logger);
    }
    return instance;
  }
}
