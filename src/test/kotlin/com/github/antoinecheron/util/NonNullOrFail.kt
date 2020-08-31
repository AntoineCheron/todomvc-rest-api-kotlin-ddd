package com.github.antoinecheron.util

import org.junit.Assert

inline fun <A>nonNullOrFail (a: A?, f: (A) -> Unit): Unit =
    if (a == null) { Assert.fail() } else { f(a) }

fun <A, B>nonNullOrFail (a: A?, b: B?, f: (A, B) -> Unit): Unit =
    if (a == null || b == null) { Assert.fail() } else { f(a,b) }

fun <A, B, C>nonNullOrFail (a: A?, b: B?, c: C?, f: (A, B, C) -> Unit): Unit =
    if (a == null || b == null || c == null) { Assert.fail() } else { f(a,b,c) }

fun <A, B, C, D>nonNullOrFail (a: A?, b: B?, c: C?, d: D?, f: (A, B, C, D) -> Unit): Unit =
    if (a == null || b == null || c == null || d == null) {
        Assert.fail()
    } else {
        f(a,b,c,d)
    }

fun <A, B, C, D, E>nonNullOrFail (a: A?, b: B?, c: C?, d: D?, e: E?, f: (A, B, C, D, E) -> Unit): Unit =
    if (a == null || b == null || c == null || d == null || e == null) {
        Assert.fail()
    } else {
        f(a,b,c,d, e)
    }
