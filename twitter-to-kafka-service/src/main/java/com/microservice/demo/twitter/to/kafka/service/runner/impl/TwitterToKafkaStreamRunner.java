package com.microservice.demo.twitter.to.kafka.service.runner.impl;

import com.microservice.demo.twitter.to.kafka.service.config.TwitterToKafkaConfigData;
import com.microservice.demo.twitter.to.kafka.service.listener.TwitterStatusListener;
import com.microservice.demo.twitter.to.kafka.service.runner.StreamRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;

@Slf4j
@Component
public class TwitterToKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final TwitterStatusListener twitterStatusListener;

    private TwitterStream twitterStream;

    public TwitterToKafkaStreamRunner(TwitterToKafkaConfigData configData, TwitterStatusListener listener) {
        this.twitterToKafkaConfigData = configData;
        this.twitterStatusListener = listener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(this.twitterStatusListener);
        FilterQuery filterQuery = new FilterQuery(this.twitterToKafkaConfigData.getTwitterKeywords().toArray(new String[0]));
        twitterStream.filter(filterQuery);
    }

    @PreDestroy
    public void shutdown() {
        if(twitterStream != null) {
            log.info("Twitter Streaming is shutting down");
            twitterStream.shutdown();
        }
    }
}
