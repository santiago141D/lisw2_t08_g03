package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado en el que la pregunta ha sido aprobada por el revisor.
 * Queda lista para ser publicada en el banco activo.
 */
public class ApprovedState implements QuestionState {

    @Override
    public void publish(Question question) {
        question.setState(new PublishedState());
    }

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("La pregunta ya ha sido aprobada.");
    }

    @Override
    public void approve(Question question) {
        throw new IllegalStateException("La pregunta ya se encuentra aprobada.");
    }

    @Override
    public void archive(Question question) {
        throw new IllegalStateException("Una pregunta aprobada debe ser publicada antes de poder archivarse.");
    }

    @Override
    public String getName() {
        return "Aprobada";
    }
}
