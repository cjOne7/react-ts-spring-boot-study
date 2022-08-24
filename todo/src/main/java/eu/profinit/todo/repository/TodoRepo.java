package eu.profinit.todo.repository;

import eu.profinit.todo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<Todo, Long> {}