package com.nklmish.springiodemo.txn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Component
public class OrderService {

    private KafkaTemplate<Object, Object> template;

    private OrderRepo repo;

    @Autowired
    public OrderService(KafkaTemplate<Object, Object> template, OrderRepo repo) {
        this.template = template;
        this.repo = repo;
    }

    @Transactional(transactionManager = "chainedTransactionManager")
    public void book() throws Exception {
        String suffix = UUID.randomUUID().toString();

        this.repo.save(new OrderItem(1L, "blueberries " + suffix));
        ListenableFuture<SendResult<Object, Object>> future = this.template.sendDefault(
                "new-item-created-" + suffix);
        future.get();


        throw new RuntimeException("Should not save anything");
//
//        if (Math.random() > 0.5) {
//        }
    }
}