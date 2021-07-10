package com.github.demo.reactivedemo.springeventlistener;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class Sensor {

  private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

  private final ApplicationEventPublisher publisher;
  private final Random random = new Random();

  @Autowired
  public Sensor(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @PostConstruct
  public void start(){
   executorService.schedule(this::prode,1,TimeUnit.SECONDS);
  }

  public void prode(){
    double temperature = 16 + random.nextGaussian() * 10;
    publisher.publishEvent(new Temperature(temperature));
    executorService.schedule(this::prode, random.nextInt(10), TimeUnit.SECONDS);
  }
}
