package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - Patrón State (Ciclo de Vida de Pregunta)")
class QuestionStateTest {

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question("Q-1", "¿Qué es polimorfismo?", "SELECCION_UNICA");
    }

    @Test
    @DisplayName("Una nueva pregunta debe iniciar siempre en estado Borrador")
    void testInitialStateIsDraft() {
        assertNotNull(question.getState());
        assertEquals("Borrador", question.getState().getName());
        assertInstanceOf(DraftState.class, question.getState());
        assertFalse(question.canBeUsedInExam(), "Una pregunta en borrador no puede usarse en exámenes");
    }

    @Test
    @DisplayName("Flujo completo válido: Borrador -> Pendiente -> En Revisión -> Aprobada -> Publicada -> Archivada")
    void testHappyPathCycle() {
        // 1. Borrador -> Pendiente de revisión
        question.submitForReview();
        assertEquals("Pendiente de revisión", question.getState().getName());
        assertFalse(question.canBeUsedInExam());

        // 2. Pendiente -> En revisión
        question.assignReviewer();
        assertEquals("En revisión", question.getState().getName());
        assertFalse(question.canBeUsedInExam());

        // 3. En revisión -> Aprobada
        question.approve();
        assertEquals("Aprobada", question.getState().getName());
        assertFalse(question.canBeUsedInExam(), "Aprobada aún no está publicada, no debe usarse en examen");

        // 4. Aprobada -> Publicada
        question.publish();
        assertEquals("Publicada", question.getState().getName());
        assertTrue(question.canBeUsedInExam(), "En estado Publicada SÍ debe poder usarse en exámenes");

        // 5. Publicada -> Archivada
        question.archive();
        assertEquals("Archivada", question.getState().getName());
        assertFalse(question.canBeUsedInExam(), "Archivada no puede usarse en exámenes activos");
    }

    @Test
    @DisplayName("Flujo de rechazo: En revisión -> Rechazada -> Retornar a Borrador")
    void testRejectionAndReturnToDraft() {
        question.submitForReview();
        question.assignReviewer();
        assertEquals("En revisión", question.getState().getName());

        question.reject();
        assertEquals("Rechazada", question.getState().getName());
        assertFalse(question.canBeUsedInExam());

        question.returnToDraft();
        assertEquals("Borrador", question.getState().getName());
        assertInstanceOf(DraftState.class, question.getState());
    }

    @Test
    @DisplayName("Transición no permitida: Borrador -> Publicada debe lanzar IllegalStateException")
    void testInvalidDraftToPublishedThrows() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> question.publish());
        assertTrue(ex.getMessage().contains("no puede ser publicada"));
    }

    @Test
    @DisplayName("Transición no permitida: Borrador -> Aprobada debe lanzar IllegalStateException")
    void testInvalidDraftToApprovedThrows() {
        assertThrows(IllegalStateException.class, () -> question.approve());
    }

    @Test
    @DisplayName("Transición no permitida: Publicada -> Borrador debe lanzar IllegalStateException")
    void testInvalidPublishedToDraftThrows() {
        question.submitForReview();
        question.assignReviewer();
        question.approve();
        question.publish();

        assertThrows(IllegalStateException.class, () -> question.returnToDraft());
    }

    @Test
    @DisplayName("Transición no permitida: Archivada -> Publicada debe lanzar IllegalStateException")
    void testInvalidArchivedToPublishedThrows() {
        question.submitForReview();
        question.assignReviewer();
        question.approve();
        question.publish();
        question.archive();

        assertThrows(IllegalStateException.class, () -> question.publish());
    }

    @Test
    @DisplayName("Transición no permitida: Rechazada -> Publicada debe lanzar IllegalStateException")
    void testInvalidRejectedToPublishedThrows() {
        question.submitForReview();
        question.assignReviewer();
        question.reject();

        assertThrows(IllegalStateException.class, () -> question.publish());
    }
}
