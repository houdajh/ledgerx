package com.ledgerx.common;

import java.util.Objects;

public final class Either<L,R> {
  private final L left;  private final R right;
  private Either(L l, R r) { this.left=l; this.right=r; }
  public static <L,R> Either<L,R> left(L l){ return new Either<>(Objects.requireNonNull(l), null); }
  public static <L,R> Either<L,R> right(R r){ return new Either<>(null, Objects.requireNonNull(r)); }
  public boolean isLeft(){ return left!=null; }
  public boolean isRight(){ return right!=null; }
  public L left(){ return left; }
  public R right(){ return right; }
}
