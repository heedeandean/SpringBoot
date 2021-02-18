package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.study.springboot.dao.ITransaction1Dao;
import com.study.springboot.dao.ITransaction2Dao;

@Service
public class BuyTicketService {

	@Autowired
	ITransaction1Dao transaction1;
	@Autowired
	ITransaction2Dao transaction2;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
//  선언적 방법
//	@Transactional(propagation=Propagation.REQUIRED) // 하나라도 에러가 나면 롤백 처리
	@Transactional(propagation=Propagation.REQUIRES_NEW) // 각각 트랜잭션 처리
	
	public int buy(String consumerId, int amount, String error) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					transaction1.pay(consumerId, amount);

					if(error.equals("1")) {int n = 10 / 0;} // 의도적 에러 발생
					
					transaction2.pay(consumerId, amount);
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println("[Transaction Propagation #2] Rollback");
			
			return 0;
		}
	}
	
}
