package com.nttdata.passive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.passive.model.Accounts;

@Repository
public interface IAccountRepository extends ReactiveMongoRepository<Accounts, String>{
}
