package com.ledgerx.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EitherNullTest {
  @Test void left_null_throws() {
    assertThrows(NullPointerException.class, () -> Either.left(null));
  }
  @Test void right_null_throws() {
    assertThrows(NullPointerException.class, () -> Either.right(null));
  }
}
