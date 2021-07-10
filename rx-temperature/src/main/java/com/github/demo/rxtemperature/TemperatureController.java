package com.github.demo.rxtemperature;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class TemperatureController {

  private final Sensor sensor;

  public TemperatureController(Sensor sensor) {
    this.sensor = sensor;
  }

  @GetMapping("/stream")
  public SseEmitter events(HttpServletRequest request) {
    RxSseEmitter rxSseEmitter = new RxSseEmitter();
    sensor.temperatureStream().subscribe(rxSseEmitter.getSubscriber());
    return rxSseEmitter;
  }

}
