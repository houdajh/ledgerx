package com.ledgerx.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EitherTryTest {
  @Test void either(){
    var l = Either.<String,Integer>left("oops");
    var r = Either.right(7);
    assertTrue(l.isLeft()); assertTrue(r.isRight());
    assertEquals("oops", l.left());
    assertEquals(7, r.right());
  }

  @Test void try_of(){
    var ok = Try.of(() -> "x".repeat(2));
    var ko = Try.<Integer>of(() -> Integer.parseInt("NaN"));
    assertTrue(ok.isSuccess()); assertEquals("xx", ok.getOrElse("?"));
    assertFalse(ko.isSuccess()); assertEquals(0, ko.getOrElse(0));
  }
}
