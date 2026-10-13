package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.domain.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Subsistema de repositorio para almacenamiento de preguntas.
 */
public class QuestionRepository {

    private final Map<String, Question> storage = new LinkedHashMap<>();

    public synchronized Question save(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta a guardar no puede ser nula.");
        }
        if (question.getId() == null || question.getId().isBlank()) {
            question.setId("Q-" + (storage.size() + 1));
        }
        storage.put(question.getId(), question);
        return question;
    }

    public synchronized Optional<Question> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(id));
    }

    public synchronized List<Question> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    public synchronized void deleteById(String id) {
        if (id != null) {
            storage.remove(id);
        }
    }

    public synchronized void clear() {
        storage.clear();
    }
}
