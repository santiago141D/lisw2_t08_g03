package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Estado en el que la pregunta se encuentra aprobada y publicada.
 * Es el único estado en el que la pregunta puede ser utilizada activamente en exámenes.
 */
public class PublishedState implements QuestionState {

    @Override
    public void archive(Question question) {
        question.setState(new ArchivedState());
    }

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("Una pregunta publicada no puede volver a enviarse a revisión.");
    }

    @Override
    public void approve(Question question) {
        throw new IllegalStateException("La pregunta ya se encuentra publicada.");
    }

    @Override
    public void publish(Question question) {
        throw new IllegalStateException("La pregunta ya está publicada.");
    }

    @Override
    public void returnToDraft(Question question) {
        throw new IllegalStateException("Una pregunta publicada no puede regresar a estado borrador.");
    }

    @Override
    public boolean canBeUsedInExam() {
        return true;
    }

    @Override
    public String getName() {
        return "Publicada";
    }
}
