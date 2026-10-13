package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Subsistema de validación de preguntas.
 */
public class QuestionValidator {

    public void validate(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        if (question.getStatement() == null || question.getStatement().isBlank()) {
            throw new IllegalArgumentException("El enunciado de la pregunta es obligatorio.");
        }
        if (question.getType() == null || question.getType().isBlank()) {
            throw new IllegalArgumentException("El tipo de pregunta es obligatorio.");
        }
    }
}
