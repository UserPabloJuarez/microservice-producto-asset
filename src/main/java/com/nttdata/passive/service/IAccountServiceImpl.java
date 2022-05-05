package com.nttdata.passive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.nttdata.passive.model.Accounts;
import com.nttdata.passive.repository.IAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class IAccountServiceImpl implements IAccountService{

	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private static final Logger LOGGER = LoggerFactory.getLogger(IAccountServiceImpl.class);
	
	@Override
	public Mono<Accounts> create(Mono<Accounts> e){
		return e.flatMap(accountRepository::insert)
				.onErrorResume(x -> {
                    LOGGER.error("[" + getClass().getName() + "][addPerson]" + x);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + x));
                }).switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                );
	}
	
	@Override
	public Mono<Accounts> update(String accountNumber, Mono<Accounts> e){
		return e.flatMap(accountRepository::save).onErrorResume(x -> {
            LOGGER.error("[" + getClass().getName() + "][setUpdatePerson]" + x);
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + x));
        }).switchIfEmpty(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
	}
	
	@Override
	 public Flux<Accounts> getAllAccounts() {
	    return accountRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][getAllPerson]" + e);
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "" + e));
        });
	 }
	
	@Override
	 public Mono<Accounts> getById(String accountNumber){
	    return  accountRepository.findById(accountNumber).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][getPersonById]" + e);
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
        }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
	 }
	
	@Override
	 public Mono<Accounts> deleteEmp(String accountNumber) {
	    return accountRepository.findById(accountNumber)
	          .flatMap(deleteEmp->accountRepository.delete(deleteEmp)
	          .then(Mono.just(deleteEmp))).onErrorResume(e -> {
                  LOGGER.error("[" + getClass().getName() + "][deletePerson]" + e);
                  return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
              });
	 }	
}
