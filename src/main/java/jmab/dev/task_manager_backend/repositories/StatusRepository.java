package jmab.dev.task_manager_backend.repositories;

import jmab.dev.task_manager_backend.model.StatusModel;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<StatusModel, Integer> {
}