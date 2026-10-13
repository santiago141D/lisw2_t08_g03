package co.edu.unicauca.taller08.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - Patrón Decorator (Características Dinámicas)")
class QuestionDecoratorTest {

    @Test
    @DisplayName("BasicQuestion retorna la descripción inicial y el puntaje por defecto de 1.0")
    void testBasicQuestion() {
        QuestionComponent basic = new BasicQuestion("¿Qué es un patrón de diseño?");
        assertEquals("¿Qué es un patrón de diseño?", basic.getDescription());
        assertEquals(1.0, basic.getScore(), 0.001);
    }

    @Test
    @DisplayName("HighDifficultyQuestion añade etiqueta [ALTA DIFICULTAD] y aumenta el puntaje en factor 1.5")
    void testHighDifficultyQuestion() {
        QuestionComponent q = new BasicQuestion("¿Demuestre P vs NP?");
        q = new HighDifficultyQuestion(q);

        assertEquals("¿Demuestre P vs NP? [ALTA DIFICULTAD]", q.getDescription());
        assertEquals(1.5, q.getScore(), 0.001);
    }

    @Test
    @DisplayName("CompetencyQuestion añade la etiqueta de competencia correspondiente")
    void testCompetencyQuestion() {
        QuestionComponent q = new BasicQuestion("Calcule el área sombreada");
        q = new CompetencyQuestion(q, "Razonamiento Cuantitativo");

        assertEquals("Calcule el área sombreada [COMPETENCIA: Razonamiento Cuantitativo]", q.getDescription());
        assertEquals(1.0, q.getScore(), 0.001);
    }

    @Test
    @DisplayName("FeedbackQuestion añade el texto explicativo de retroalimentación")
    void testFeedbackQuestion() {
        QuestionComponent q = new BasicQuestion("Pregunta conceptual");
        q = new FeedbackQuestion(q, "Recuerde aplicar el principio SOLID Open/Closed");

        assertEquals("Pregunta conceptual [RETROALIMENTACIÓN: Recuerde aplicar el principio SOLID Open/Closed]", q.getDescription());
    }

    @Test
    @DisplayName("MarkedForReviewQuestion añade etiqueta de revisión sin alterar el puntaje")
    void testMarkedForReviewQuestion() {
        QuestionComponent q = new BasicQuestion("Pregunta bajo sospecha");
        q = new MarkedForReviewQuestion(q);

        assertEquals("Pregunta bajo sospecha [MARCADA PARA REVISIÓN]", q.getDescription());
        assertEquals(1.0, q.getScore(), 0.001);
    }

    @Test
    @DisplayName("Composición múltiple: anidar varios decoradores acumula correctamente todas las descripciones y puntaje")
    void testMultipleStackedDecorators() {
        QuestionComponent q = new BasicQuestion("Pregunta integrada");
        q = new HighDifficultyQuestion(q);
        q = new CompetencyQuestion(q, "Lectura Crítica");
        q = new ContextualizedQuestion(q, "Ensayo sobre inteligencia artificial");
        q = new FeedbackQuestion(q, "Analice los argumentos inductivos");
        q = new MarkedForReviewQuestion(q);

        String desc = q.getDescription();
        assertTrue(desc.contains("Pregunta integrada"));
        assertTrue(desc.contains("[ALTA DIFICULTAD]"));
        assertTrue(desc.contains("[COMPETENCIA: Lectura Crítica]"));
        assertTrue(desc.contains("[CONTEXTO: Ensayo sobre inteligencia artificial]"));
        assertTrue(desc.contains("[RETROALIMENTACIÓN: Analice los argumentos inductivos]"));
        assertTrue(desc.contains("[MARCADA PARA REVISIÓN]"));
        assertEquals(1.5, q.getScore(), 0.001);
    }
}
