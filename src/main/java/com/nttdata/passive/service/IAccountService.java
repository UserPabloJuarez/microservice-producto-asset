package com.nttdata.passive.service;

import com.nttdata.passive.model.Accounts;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {

	Mono<Accounts> create(Mono <Accounts> e);
	
	Mono<Accounts> update(String accountNumber, Mono <Accounts> e);
	
	Flux<Accounts> getAllAccounts();
	
	Mono<Accounts> getById(String accountNumber);
	
	Mono<Accounts> deleteEmp(String accountNumber);
	
}
