package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests for Driver class
 */
class DriverTest {

  /**
   * Tests for main method
   */
  @Test
  public void testMain() {
    assertDoesNotThrow(() -> Driver.main(new String[]{"0.0.0.0", "35001"}));
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[]{"0.0.0.0", "35001", "argument 3"}));
    assertThrows(IllegalArgumentException.class, () -> Driver.main(new String[]{"0.0.0.0"}));
  }

}