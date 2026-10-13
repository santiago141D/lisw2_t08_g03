package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado inicial de la pregunta. Permite completar su contenido y enviarla a revisión.
 */
public class DraftState implements QuestionState {

    @Override
    public void submitForReview(Question question) {
        question.setState(new PendingReviewState());
    }

    @Override
    public void approve(Question question) {
        throw new IllegalStateException("Una pregunta en borrador no puede ser aprobada directamente.");
    }

    @Override
    public void publish(Question question) {
        throw new IllegalStateException("Una pregunta en borrador no puede ser publicada directamente.");
    }

    @Override
    public void archive(Question question) {
        throw new IllegalStateException("Una pregunta en borrador no puede ser archivada.");
    }

    @Override
    public String getName() {
        return "Borrador";
    }
}
