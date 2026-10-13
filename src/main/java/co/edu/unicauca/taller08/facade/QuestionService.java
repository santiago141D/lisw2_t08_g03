package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.domain.Question;
import co.edu.unicauca.taller08.state.DraftState;
import co.edu.unicauca.taller08.state.PendingReviewState;
import co.edu.unicauca.taller08.state.ReviewState;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Subsistema de servicio de negocio para la gestión de preguntas.
 */
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "El repositorio no puede ser nulo.");
    }

    public Question save(Question question) {
        return repository.save(question);
    }

    public Optional<Question> findById(String id) {
        return repository.findById(id);
    }

    public List<Question> findAll() {
        return repository.findAll();
    }

    /**
     * Aprueba una pregunta llevando a cabo las transiciones de estado requeridas.
     */
    public void approve(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        if (question.getState() instanceof DraftState) {
            question.submitForReview();
            question.assignReviewer();
            question.approve();
        } else if (question.getState() instanceof PendingReviewState) {
            question.assignReviewer();
            question.approve();
        } else if (question.getState() instanceof ReviewState) {
            question.approve();
        }
        save(question);
    }
}
