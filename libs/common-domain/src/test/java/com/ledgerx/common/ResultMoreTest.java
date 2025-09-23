package com.ledgerx.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultMoreTest {
  @Test void ok_flags() {
    var r = Result.ok("ok");
    assertTrue(r.isOk());
    assertFalse(r.isErr());
  }

  @Test void err_throws_with_code_in_message() {
    var r = Result.err("E42", "boom");
    assertTrue(r.isErr());
    var ex = assertThrows(IllegalStateException.class, r::getOrThrow);
    assertTrue(ex.getMessage().contains("E42"));
  }
}
