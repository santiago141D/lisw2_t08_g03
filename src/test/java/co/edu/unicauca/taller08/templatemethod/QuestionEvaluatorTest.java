package co.edu.unicauca.taller08.templatemethod;

import co.edu.unicauca.taller08.domain.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - Patrón Template Method (Evaluación de Preguntas)")
class QuestionEvaluatorTest {

    @Test
    @DisplayName("SingleChoiceEvaluator evalúa exitosamente respuesta correcta ignorando mayúsculas y espacios")
    void testSingleChoiceCorrect() {
        Question q = new Question("Q-1", "¿Cuál es la capital de Colombia?", "SELECCION_UNICA",
                List.of("Medellín", "Bogotá", "Cali"), List.of("Bogotá"));

        SingleChoiceEvaluator evaluator = new SingleChoiceEvaluator();
        assertTrue(evaluator.evaluate(q, List.of("Bogotá")));
        assertTrue(evaluator.evaluate(q, List.of("  bogotá  ")));
    }

    @Test
    @DisplayName("SingleChoiceEvaluator reprueba respuesta errónea")
    void testSingleChoiceIncorrect() {
        Question q = new Question("Q-1", "¿Cuál es la capital de Colombia?", "SELECCION_UNICA",
                List.of("Medellín", "Bogotá", "Cali"), List.of("Bogotá"));

        SingleChoiceEvaluator evaluator = new SingleChoiceEvaluator();
        assertFalse(evaluator.evaluate(q, List.of("Medellín")));
    }

    @Test
    @DisplayName("SingleChoiceEvaluator lanza IllegalArgumentException si se envían múltiples respuestas o ninguna")
    void testSingleChoiceValidation() {
        Question q = new Question("Q-1", "¿Enunciado?", "SELECCION_UNICA", List.of("A", "B"), List.of("A"));
        SingleChoiceEvaluator evaluator = new SingleChoiceEvaluator();

        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate(q, List.of()));
        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate(q, List.of("A", "B")));
    }

    @Test
    @DisplayName("MultipleChoiceEvaluator evalúa exitosamente cuando el estudiante selecciona todas las correctas")
    void testMultipleChoiceCorrect() {
        Question q = new Question("Q-2", "Seleccione los números primos menores a 10", "SELECCION_MULTIPLE",
                List.of("2", "3", "4", "5"), List.of("2", "3", "5"));

        MultipleChoiceEvaluator evaluator = new MultipleChoiceEvaluator();
        // El orden de la lista no debe importar porque compara conjuntos
        assertTrue(evaluator.evaluate(q, List.of("5", "2", "3")));
    }

    @Test
    @DisplayName("MultipleChoiceEvaluator reprueba si faltan opciones o se selecciona una errónea")
    void testMultipleChoiceIncorrect() {
        Question q = new Question("Q-2", "Seleccione los números primos", "SELECCION_MULTIPLE",
                List.of("2", "3", "4"), List.of("2", "3"));

        MultipleChoiceEvaluator evaluator = new MultipleChoiceEvaluator();
        assertFalse(evaluator.evaluate(q, List.of("2")), "Incompleta debe ser false");
        assertFalse(evaluator.evaluate(q, List.of("2", "3", "4")), "Con distractores debe ser false");
    }

    @Test
    @DisplayName("TrueFalseEvaluator evalúa correctamente respuestas Verdadero/Falso en distintos formatos")
    void testTrueFalseCorrect() {
        Question qTrue = new Question("Q-3", "Java es un lenguaje tipado estáticamente", "VERDADERO_FALSO",
                List.of("Verdadero", "Falso"), List.of("VERDADERO"));

        TrueFalseEvaluator evaluator = new TrueFalseEvaluator();
        assertTrue(evaluator.evaluate(qTrue, List.of("verdadero")));
        assertTrue(evaluator.evaluate(qTrue, List.of("V")));
        assertTrue(evaluator.evaluate(qTrue, List.of("TRUE")));

        assertFalse(evaluator.evaluate(qTrue, List.of("FALSO")));
        assertFalse(evaluator.evaluate(qTrue, List.of("F")));
    }

    @Test
    @DisplayName("TrueFalseEvaluator valida que el valor ingresado corresponda a un booleano válido")
    void testTrueFalseValidation() {
        Question q = new Question("Q-3", "Afirmación", "VERDADERO_FALSO", List.of("V", "F"), List.of("V"));
        TrueFalseEvaluator evaluator = new TrueFalseEvaluator();

        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate(q, List.of("Quizás")));
        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate(q, List.of()));
    }
}
