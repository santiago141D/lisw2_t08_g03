package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado en el que la pregunta tiene su contenido completo y espera asignación de revisor.
 */
public class PendingReviewState implements QuestionState {

    @Override
    public void assignReviewer(Question question) {
        question.setState(new ReviewState());
    }

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("La pregunta ya se encuentra pendiente de revisión.");
    }

    @Override
    public void approve(Question question) {
        throw new IllegalStateException("Una pregunta pendiente de revisión debe pasar primero por un revisor.");
    }

    @Override
    public void publish(Question question) {
        throw new IllegalStateException("Una pregunta pendiente de revisión no puede ser publicada.");
    }

    @Override
    public void archive(Question question) {
        throw new IllegalStateException("Una pregunta pendiente de revisión no puede ser archivada.");
    }

    @Override
    public String getName() {
        return "Pendiente de revisión";
    }
}
