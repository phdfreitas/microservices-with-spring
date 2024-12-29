package dev.phdfreitas.bookservice.repository;

import dev.phdfreitas.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
