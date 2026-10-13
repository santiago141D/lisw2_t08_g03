package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado en el que un revisor analiza la calidad y estructura de la pregunta.
 * Permite aprobar o rechazar la pregunta.
 */
public class ReviewState implements QuestionState {

    @Override
    public void approve(Question question) {
        question.setState(new ApprovedState());
    }

    @Override
    public void reject(Question question) {
        question.setState(new RejectedState());
    }

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("La pregunta ya se encuentra actualmente en revisión.");
    }

    @Override
    public void publish(Question question) {
        throw new IllegalStateException("Una pregunta en revisión no puede ser publicada hasta ser aprobada.");
    }

    @Override
    public void archive(Question question) {
        throw new IllegalStateException("Una pregunta en revisión no puede ser archivada directamente.");
    }

    @Override
    public String getName() {
        return "En revisión";
    }
}
