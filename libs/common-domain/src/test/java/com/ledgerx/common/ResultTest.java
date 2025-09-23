package com.ledgerx.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {
  @Test void ok_gets_value(){
    var r = Result.ok(42);
    assertTrue(r.isOk());
    assertEquals(42, r.getOrThrow());
  }
  @Test void err_throws(){
    var r = Result.err("E1","boom");
    assertTrue(r.isErr());
    var ex = assertThrows(IllegalStateException.class, r::getOrThrow);
    assertTrue(ex.getMessage().contains("E1"));
  }
}
