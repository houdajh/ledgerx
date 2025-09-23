package com.ledgerx.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TryMoreTest {
  @Test void success_keeps_value() {
    var t = Try.of(() -> "xx");
    assertTrue(t.isSuccess());
    assertEquals("xx", t.getOrElse("?"));
  }

  @Test void failure_returns_fallback() {
    var t = Try.<Integer>of(() -> Integer.parseInt("NaN"));
    assertFalse(t.isSuccess());
    assertEquals(0, t.getOrElse(0));
  }
}
