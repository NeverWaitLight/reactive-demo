package com.github.demo.springeventlistener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
public class TemperatureController {

  @Autowired
  private Sensor sensor;

  private final CopyOnWriteArrayList<SseEmitter> clients = new CopyOnWriteArrayList<>();

  @GetMapping("/stream")
  public SseEmitter subscribe() {
    SseEmitter sseEmitter = new SseEmitter();
    clients.add(sseEmitter);
    sseEmitter.onTimeout(() -> clients.remove(sseEmitter));
    sseEmitter.onCompletion(() -> clients.remove(sseEmitter));
    return sseEmitter;
  }

  @Async
  @EventListener
  public void listener(Temperature temperature) {
    log.info("Listen new temperature:{}", temperature.getValue());
    List<SseEmitter> deadClients = new ArrayList<>();
    clients.forEach(sseEmitter -> {
      try {
        sseEmitter.send(temperature, MediaType.APPLICATION_JSON);
      } catch (IOException e) {
        deadClients.add(sseEmitter);
      }
    });
    deadClients.removeAll(deadClients);
  }
}

