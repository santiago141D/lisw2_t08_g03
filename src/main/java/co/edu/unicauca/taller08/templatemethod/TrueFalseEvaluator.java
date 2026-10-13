package co.edu.unicauca.taller08.templatemethod;

import co.edu.unicauca.taller08.domain.Question;

import java.util.List;
import java.util.Locale;

/**
 * Evaluador concreto para preguntas de tipo Verdadero / Falso.
 */
public class TrueFalseEvaluator extends QuestionEvaluator {

    @Override
    protected void validate(Question question, List<String> answers) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar Verdadero o Falso.");
        }
        if (answers.size() != 1) {
            throw new IllegalArgumentException("Una pregunta de Verdadero/Falso solo admite 1 respuesta.");
        }
        String val = answers.get(0).trim().toUpperCase(Locale.ROOT);
        if (!isTrueOrFalse(val)) {
            throw new IllegalArgumentException("La respuesta '" + answers.get(0) + "' no es válida. Debe ser Verdadero o Falso.");
        }
        if (question.getCorrectAnswers() == null || question.getCorrectAnswers().isEmpty()) {
            throw new IllegalStateException("La pregunta no tiene definida la respuesta correcta de Verdadero/Falso.");
        }
    }

    @Override
    protected boolean compareAnswers(Question question, List<String> answers) {
        boolean studentBool = toBoolean(answers.get(0));
        boolean expectedBool = toBoolean(question.getCorrectAnswers().get(0));
        return studentBool == expectedBool;
    }

    private boolean isTrueOrFalse(String val) {
        return val.equals("VERDADERO") || val.equals("FALSO")
                || val.equals("V") || val.equals("F")
                || val.equals("TRUE") || val.equals("FALSE")
                || val.equals("T");
    }

    private boolean toBoolean(String val) {
        String normalized = val.trim().toUpperCase(Locale.ROOT);
        return normalized.equals("VERDADERO") || normalized.equals("V") || normalized.equals("TRUE") || normalized.equals("T");
    }
}
