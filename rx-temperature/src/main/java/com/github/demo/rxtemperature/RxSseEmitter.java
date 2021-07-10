package com.github.demo.rxtemperature;

import java.io.IOException;
import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

public class RxSseEmitter extends SseEmitter {

  static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
  @Getter
  private final Subscriber<Temperature> subscriber;

  public RxSseEmitter() {
    super(SSE_SESSION_TIMEOUT);
    this.subscriber = new Subscriber<Temperature>() {


      @Override
      public void onNext(Temperature temperature) {
        try {
          RxSseEmitter.this.send(temperature);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

    };
  }
}
