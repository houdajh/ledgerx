package com.ledgerx.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TryTest {

  @Test
  void success_keeps_value_and_flag() {
    var t = Try.of(() -> "xx");
    assertTrue(t.isSuccess());
    assertEquals("xx", t.getOrElse("?"));
  }

  @Test
  void failure_returns_fallback_and_flag() {
    var t = Try.<Integer>of(() -> Integer.parseInt("NaN"));
    assertFalse(t.isSuccess());
    assertEquals(0, t.getOrElse(0));
  }
}
