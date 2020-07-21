package com.lib.webLibrary.repo;

import com.lib.webLibrary.models.BookInstance;
import org.springframework.data.repository.CrudRepository;

public interface BookInstanceRepository extends CrudRepository<BookInstance,Long> {
}
