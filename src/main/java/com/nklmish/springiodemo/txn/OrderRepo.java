
package com.nklmish.springiodemo.txn;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<OrderItem, Long> {

}