package com.demacia.test.utils

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T : Any> Single<T>.subscribeOnIo(): Single<T> = subscribeOn(Schedulers.io())