package ru.magdiev.springExample.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.magdiev.springExample.entity.Message;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
