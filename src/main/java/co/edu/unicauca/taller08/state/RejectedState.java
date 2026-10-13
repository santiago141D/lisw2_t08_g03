package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado en el que la pregunta fue rechazada por el revisor debido a no conformidades.
 * Permite retornar al estado Borrador para que el autor realice los ajustes pertinentes.
 */
public class RejectedState implements QuestionState {

    @Override
    public void returnToDraft(Question question) {
        question.setState(new DraftState());
    }

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("Una pregunta rechazada debe retornar primero a borrador para ajustes.");
    }

    @Override
    public void approve(Question question) {
        throw new IllegalStateException("Una pregunta rechazada no puede ser aprobada directamente sin corrección.");
    }

    @Override
    public void publish(Question question) {
        throw new IllegalStateException("Una pregunta rechazada no puede ser publicada.");
    }

    @Override
    public void archive(Question question) {
        throw new IllegalStateException("Una pregunta rechazada no puede ser archivada directamente.");
    }

    @Override
    public String getName() {
        return "Rechazada";
    }
}
