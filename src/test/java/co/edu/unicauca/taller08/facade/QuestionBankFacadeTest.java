package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.adapter.ExternalQuestionAdapter;
import co.edu.unicauca.taller08.adapter.ExternalQuestionService;
import co.edu.unicauca.taller08.decorator.HighDifficultyQuestion;
import co.edu.unicauca.taller08.decorator.QuestionComponent;
import co.edu.unicauca.taller08.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - Patrón Facade (Fachada del Banco de Preguntas)")
class QuestionBankFacadeTest {

    private QuestionBankFacade facade;

    @BeforeEach
    void setUp() {
        facade = new QuestionBankFacade();
    }

    @Test
    @DisplayName("Facade crea y valida una nueva pregunta en el repositorio")
    void testCreateQuestion() {
        Question q = new Question("Q-1", "¿Qué patrón encapsula una familia de algoritmos?", "SELECCION_UNICA",
                List.of("Strategy", "Observer"), List.of("Strategy"));

        facade.createQuestion(q);

        Optional<Question> found = facade.findQuestionById("Q-1");
        assertTrue(found.isPresent());
        assertEquals("¿Qué patrón encapsula una familia de algoritmos?", found.get().getStatement());
    }

    @Test
    @DisplayName("Facade valida y rechaza preguntas con enunciado vacío")
    void testCreateInvalidQuestionThrows() {
        Question q = new Question("Q-INV", "", "SELECCION_UNICA");
        assertThrows(IllegalArgumentException.class, () -> facade.createQuestion(q));
    }

    @Test
    @DisplayName("Facade publica una pregunta llevándola a estado Publicada")
    void testPublishQuestion() {
        Question q = new Question("Q-PUB", "¿Qué patrón notifica cambios?", "SELECCION_UNICA",
                List.of("Observer"), List.of("Observer"));

        facade.createQuestion(q);
        facade.publishQuestion(q);

        assertEquals("Publicada", q.getState().getName());
        assertTrue(q.canBeUsedInExam());

        List<Question> published = facade.getPublishedQuestions();
        assertEquals(1, published.size());
        assertEquals("Q-PUB", published.get(0).getId());
    }

    @Test
    @DisplayName("Facade evalúa respuestas invocando el Template Method correspondiente")
    void testEvaluateAnswerThroughFacade() {
        Question q = new Question("Q-EVAL", "Verdadero o falso: Java es multiplataforma", "VERDADERO_FALSO",
                List.of("Verdadero", "Falso"), List.of("VERDADERO"));
        facade.createQuestion(q);

        assertTrue(facade.evaluateAnswer(q, List.of("VERDADERO")));
        assertFalse(facade.evaluateAnswer(q, List.of("FALSO")));
        assertTrue(facade.evaluateAnswer("Q-EVAL", List.of("V")));
    }

    @Test
    @DisplayName("Facade importa una pregunta externa usando el Adapter y la guarda en el banco")
    void testImportExternalQuestionThroughFacade() {
        ExternalQuestionService externalService = new ExternalQuestionService("""
        {
            "statement": "¿Qué es un microkernel?",
            "answer": "Arquitectura modular con núcleo mínimo"
        }
        """);

        Question imported = facade.importExternalQuestion(new ExternalQuestionAdapter(externalService));

        assertNotNull(imported);
        assertEquals("¿Qué es un microkernel?", imported.getStatement());

        Optional<Question> found = facade.findQuestionById(imported.getId());
        assertTrue(found.isPresent());
    }

    @Test
    @DisplayName("Facade asComponent permite decorar preguntas de manera transparente")
    void testDecorateThroughFacade() {
        Question q = new Question("Q-DEC", "¿Qué es polimorfismo?", "SELECCION_UNICA");
        facade.createQuestion(q);

        QuestionComponent comp = facade.asComponent(q);
        comp = new HighDifficultyQuestion(comp);

        assertTrue(comp.getDescription().contains("[ALTA DIFICULTAD]"));
        assertEquals(1.5, comp.getScore(), 0.001);
    }
}
