package io.neocdtv.player.core.omxplayer;

import io.neocdtv.player.core.PlayerState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * OmxPlayerErrorStreamConsumer.
 *
 * @author xix
 * @since 03.01.18
 */
public class OmxPlayerErrorStreamConsumer implements Runnable {

  private final static Logger LOGGER = Logger.getLogger(OmxPlayerErrorStreamConsumer.class.getName());

  private final InputStream in;
  private boolean active = true;

  public OmxPlayerErrorStreamConsumer(InputStream in) {
    this.in = in;
  }

  public void run() {
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(in));
      String line = null;
      while (active && (line = bufferedReader.readLine()) != null) {
        LOGGER.log(Level.FINE, line);
      }
    } catch (Exception exception) {
      LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException ioException) {
        LOGGER.log(Level.SEVERE, ioException.getMessage(), ioException);
      }
    }
  }

  public void deactivate() {
    active = false;
  }
}
