package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado en el que la pregunta ha sido retirada de uso activo y archivada para histórico.
 */
public class ArchivedState implements QuestionState {

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("Una pregunta archivada no puede enviarse a revisión.");
    }

    @Override
    public void approve(Question question) {
        throw new IllegalStateException("Una pregunta archivada no puede ser aprobada.");
    }

    @Override
    public void publish(Question question) {
        throw new IllegalStateException("Una pregunta archivada no puede ser publicada directamente.");
    }

    @Override
    public void archive(Question question) {
        throw new IllegalStateException("La pregunta ya se encuentra archivada.");
    }

    @Override
    public String getName() {
        return "Archivada";
    }
}
