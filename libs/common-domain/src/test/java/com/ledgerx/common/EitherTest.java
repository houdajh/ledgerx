package com.ledgerx.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EitherTest {

  @Test
  void left_and_right_accessors_and_flags() {
    var l = Either.<String, Integer>left("oops");
    var r = Either.right(7);

    assertTrue(l.isLeft());
    assertFalse(l.isRight());
    assertEquals("oops", l.left());
    assertNull(l.right());

    assertFalse(r.isLeft());
    assertTrue(r.isRight());
    assertNull(r.left());
    assertEquals(7, r.right());
  }

  @Test
  void null_inputs_are_rejected() {
    // These assume your Either.left/right use Objects.requireNonNull
    assertThrows(NullPointerException.class, () -> Either.left(null));
    assertThrows(NullPointerException.class, () -> Either.right(null));
  }
}
