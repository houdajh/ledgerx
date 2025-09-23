package com.ledgerx.common;
import java.util.function.Supplier;

public sealed interface Try<T> permits Try.Success, Try.Failure {
  record Success<T>(T value) implements Try<T> {}
  record Failure<T>(Throwable error) implements Try<T> {}

  static <T> Try<T> of(Supplier<T> s){
    try { return new Success<>(s.get()); } catch(Throwable t){ return new Failure<>(t); }
  }

  default boolean isSuccess(){ return this instanceof Success<?>; }
  default T getOrElse(T fallback){ return this instanceof Success<T> s ? s.value() : fallback; }
}
