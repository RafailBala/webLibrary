package com.lib.webLibrary.repo;

import com.lib.webLibrary.models.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department,Long> {
}
