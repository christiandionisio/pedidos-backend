package com.cdionisio.pedidos.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepo <T> extends ReactiveMongoRepository<T, String> {
}
