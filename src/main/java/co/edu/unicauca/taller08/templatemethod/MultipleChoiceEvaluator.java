package co.edu.unicauca.taller08.templatemethod;

import co.edu.unicauca.taller08.domain.Question;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Evaluador concreto para preguntas de Selección Múltiple con múltiples respuestas correctas.
 */
public class MultipleChoiceEvaluator extends QuestionEvaluator {

    @Override
    protected void validate(Question question, List<String> answers) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una opción seleccionada.");
        }
        if (question.getCorrectAnswers() == null || question.getCorrectAnswers().isEmpty()) {
            throw new IllegalStateException("La pregunta no tiene definidas respuestas correctas.");
        }
    }

    @Override
    protected boolean compareAnswers(Question question, List<String> answers) {
        Set<String> studentNormalized = answers.stream()
                .filter(a -> a != null && !a.isBlank())
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        Set<String> expectedNormalized = question.getCorrectAnswers().stream()
                .filter(a -> a != null && !a.isBlank())
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        return studentNormalized.equals(expectedNormalized);
    }
}
