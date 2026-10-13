package co.edu.unicauca.taller08.decorator;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Componente concreto básico en el patrón Decorator.
 * Representa una pregunta básica con puntaje por defecto de 1.0.
 */
public class BasicQuestion implements QuestionComponent {

    private String description;

    public BasicQuestion(String description) {
        this.description = description != null ? description.trim() : "";
    }

    public BasicQuestion(Question question) {
        this(question != null ? question.getStatement() : "");
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getScore() {
        return 1.0;
    }
}
