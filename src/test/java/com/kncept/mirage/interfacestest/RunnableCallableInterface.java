package com.kncept.mirage.interfacestest;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface RunnableCallableInterface extends Runnable, Callable<Future> {
}
