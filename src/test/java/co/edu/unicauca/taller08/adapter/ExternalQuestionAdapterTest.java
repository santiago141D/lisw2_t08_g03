package co.edu.unicauca.taller08.adapter;

import co.edu.unicauca.taller08.domain.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - Patrón Adapter (Importar Preguntas Externas)")
class ExternalQuestionAdapterTest {

    @Test
    @DisplayName("ExternalQuestionAdapter transforma exitosamente datos externos JSON en una Question válida")
    void testAdapterTransformsExternalJson() {
        ExternalQuestionService externalService = new ExternalQuestionService();
        ExternalQuestionAdapter adapter = new ExternalQuestionAdapter(externalService);

        Question q = adapter.getQuestion();
        assertNotNull(q);
        assertNotNull(q.getId());
        assertEquals("¿Cuál es la capital de Colombia?", q.getStatement());
        assertEquals("Borrador", q.getState().getName());
        assertEquals(1, q.getCorrectAnswers().size());
        assertEquals("Bogotá", q.getCorrectAnswers().get(0));
    }

    @Test
    @DisplayName("ExternalQuestionAdapter maneja servicios con diferentes cargas JSON")
    void testAdapterWithCustomPayload() {
        String json = """
        {
            "statement": "¿Qué patrón provee interfaz unificada?",
            "answer": "Facade"
        }
        """;
        ExternalQuestionService service = new ExternalQuestionService(json);
        ExternalQuestionAdapter adapter = new ExternalQuestionAdapter(service);

        Question q = adapter.getQuestion();
        assertEquals("¿Qué patrón provee interfaz unificada?", q.getStatement());
        assertEquals("Facade", q.getCorrectAnswers().get(0));
    }

    @Test
    @DisplayName("ExternalQuestionAdapter lanza IllegalStateException si el servicio retorna respuesta vacía")
    void testAdapterEmptyPayloadThrows() {
        ExternalQuestionService service = new ExternalQuestionService("");
        ExternalQuestionAdapter adapter = new ExternalQuestionAdapter(service);

        assertThrows(IllegalStateException.class, () -> adapter.getQuestion());
    }
}
