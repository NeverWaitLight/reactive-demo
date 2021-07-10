package com.github.demo.rxtemperature;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class Sensor {
  private final Random rnd = new Random();
  private final Observable<Temperature> dataStream = Observable.range(0, Integer.MAX_VALUE)
      .concatMap(tick -> Observable.just(tick).delay(rnd.nextInt(20), TimeUnit.SECONDS)
          .map(tickValue -> this.prode()))
      .publish()
      .refCount();

  public Temperature prode() {
    return new Temperature(16 + rnd.nextGaussian() * 10);
  }

  public Observable<Temperature> temperatureStream() {
    return dataStream;
  }
}
