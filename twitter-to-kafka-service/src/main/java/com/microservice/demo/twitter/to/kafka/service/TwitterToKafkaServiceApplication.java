package com.microservice.demo.twitter.to.kafka.service;

import com.microservice.demo.twitter.to.kafka.service.config.TwitterToKafkaConfigData;
import com.microservice.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.microservice.demo")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final StreamRunner streamRunner;

    public TwitterToKafkaServiceApplication(TwitterToKafkaConfigData twitterToKafkaConfigData,
                                            StreamRunner streamRunner) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.streamRunner = streamRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(this.twitterToKafkaConfigData.getTwitterKeywords().toString());
        streamRunner.start();
    }
}
