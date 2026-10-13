package co.edu.unicauca.taller08.templatemethod;

import co.edu.unicauca.taller08.domain.Question;

import java.util.List;

/**
 * Clase base abstracta que define el algoritmo general para la evaluación de preguntas
 * mediante el patrón Template Method.
 * <p>
 * El método {@link #evaluate(Question, List)} es el esqueleto invariable (final),
 * delegando la validación y la comparación en los métodos primitivos que cada
 * subclase concreta implementa según su tipo de pregunta.
 */
public abstract class QuestionEvaluator {

    /**
     * Algoritmo plantilla para evaluar respuestas de un estudiante.
     * Invariable en todas las subclases.
     *
     * @param question Pregunta a evaluar.
     * @param answers Lista de respuestas proporcionadas por el estudiante.
     * @return true si la respuesta es correcta, false en caso contrario.
     */
    public final boolean evaluate(Question question, List<String> answers) {
        validate(question, answers);
        return compareAnswers(question, answers);
    }

    /**
     * Paso variable: valida que la estructura y número de respuestas sean admisibles
     * para el tipo específico de pregunta.
     *
     * @throws IllegalArgumentException si los argumentos son inválidos.
     */
    protected abstract void validate(Question question, List<String> answers);

    /**
     * Paso variable: compara las respuestas del estudiante contra las respuestas
     * esperadas por la pregunta.
     */
    protected abstract boolean compareAnswers(Question question, List<String> answers);
}
