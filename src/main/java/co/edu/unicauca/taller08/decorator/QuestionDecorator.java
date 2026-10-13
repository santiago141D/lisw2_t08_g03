package co.edu.unicauca.taller08.decorator;

import java.util.Objects;

/**
 * Decorador abstracto base en el patrón Decorator.
 * Mantiene una referencia al componente envuelto y delega en él por defecto.
 */
public abstract class QuestionDecorator implements QuestionComponent {

    protected QuestionComponent question;

    public QuestionDecorator(QuestionComponent question) {
        this.question = Objects.requireNonNull(question, "El componente a decorar no puede ser nulo.");
    }

    @Override
    public String getDescription() {
        return question.getDescription();
    }

    @Override
    public double getScore() {
        return question.getScore();
    }
}
