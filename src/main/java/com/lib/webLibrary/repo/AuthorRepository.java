package com.lib.webLibrary.repo;

import com.lib.webLibrary.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Long> {
}
