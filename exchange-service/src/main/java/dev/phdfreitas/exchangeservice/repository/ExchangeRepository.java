package dev.phdfreitas.exchangeservice.repository;

import dev.phdfreitas.exchangeservice.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Exchange findByFromAndTo(String from, String to);
}
