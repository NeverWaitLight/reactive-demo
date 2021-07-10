package com.github.demo.reactivedemo.rxdemo;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class Rx1Test {

  public static void main(String[] args) {
    modern();
    System.out.println("--------------------");
    traditional();
  }

  public static void modern() {
    Observable.create(subscriber -> {
      subscriber.onNext("Hello lambda rx java");
      subscriber.onCompleted();
    }).subscribe(
        System.out::println,
        System.out::println,
        () -> System.out.println("Lambda done")
    );
  }

  public static void traditional() {
    Subscriber<String> subscriber = new Subscriber<>() {

      @Override
      public void onCompleted() {
        System.out.println("Done!");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println(e.getMessage());
      }

      @Override
      public void onNext(String s) {
        System.out.println(s);
      }
    };
    Observable<String> observable = Observable.create(new OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("Hello rx java");
        subscriber.onCompleted();
      }
    });
    observable.subscribe(subscriber);
  }


}
