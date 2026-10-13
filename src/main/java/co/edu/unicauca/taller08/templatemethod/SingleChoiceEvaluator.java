package co.edu.unicauca.taller08.templatemethod;

import co.edu.unicauca.taller08.domain.Question;

import java.util.List;

/**
 * Evaluador concreto para preguntas de Selección Única con una sola respuesta correcta.
 */
public class SingleChoiceEvaluator extends QuestionEvaluator {

    @Override
    protected void validate(Question question, List<String> answers) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar una respuesta para la evaluación.");
        }
        if (answers.size() != 1) {
            throw new IllegalArgumentException("Una pregunta de selección única requiere exactamente 1 respuesta seleccionada.");
        }
        if (question.getCorrectAnswers() == null || question.getCorrectAnswers().isEmpty()) {
            throw new IllegalStateException("La pregunta no tiene definida una respuesta correcta.");
        }
    }

    @Override
    protected boolean compareAnswers(Question question, List<String> answers) {
        String studentAnswer = answers.get(0).trim();
        String expectedAnswer = question.getCorrectAnswers().get(0).trim();
        return studentAnswer.equalsIgnoreCase(expectedAnswer);
    }
}
