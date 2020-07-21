package com.lib.webLibrary.repo;

import com.lib.webLibrary.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {

}
