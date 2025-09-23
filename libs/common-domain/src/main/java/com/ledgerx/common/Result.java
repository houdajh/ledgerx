package com.ledgerx.common;


public sealed interface Result<T> permits Result.Ok, Result.Err {
    record Ok<T>(T value) implements Result<T> {}
    record Err<T>(String code, String message) implements Result<T> {}

    static <T> Result<T> ok(T v) { return new Ok<>(v); }
    static <T> Result<T> err(String code, String msg) { return new Err<>(code, msg); }

    default boolean isOk() { return this instanceof Ok<?>; }
    default boolean isErr() { return this instanceof Err<?>; }

    @SuppressWarnings("unchecked")
    default T getOrThrow() {
        if (this instanceof Ok<?> o) return (T) o.value();
        // Sealed interface guarantees: the only other possibility is Err
        Err<?> e = (Err<?>) this;
        throw new IllegalStateException(e.code() + ": " + e.message());
    }

}
