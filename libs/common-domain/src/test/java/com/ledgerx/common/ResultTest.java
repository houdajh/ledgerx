package com.ledgerx.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

  @Test
  void ok_flags_and_get() {
    var r = Result.ok(42);
    assertTrue(r.isOk());
    assertFalse(r.isErr());
    assertEquals(42, r.getOrThrow());
  }

  @Test
  void err_flags_and_get_throws_with_code_in_message() {
    var e = Result.err("E42", "boom");
    assertTrue(e.isErr());
    assertFalse(e.isOk());
    var ex = assertThrows(IllegalStateException.class, e::getOrThrow);
    assertTrue(ex.getMessage().contains("E42"));
  }
}
