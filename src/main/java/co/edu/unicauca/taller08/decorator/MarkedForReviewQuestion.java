package co.edu.unicauca.taller08.decorator;

/**
 * Decorador concreto para preguntas marcadas para revisión docente o técnica.
 */
public class MarkedForReviewQuestion extends QuestionDecorator {

    public MarkedForReviewQuestion(QuestionComponent question) {
        super(question);
    }

    @Override
    public String getDescription() {
        return question.getDescription() + " [MARCADA PARA REVISIÓN]";
    }
}
