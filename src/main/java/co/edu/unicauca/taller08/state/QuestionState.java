package co.edu.unicauca.taller08.state;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Interfaz que define las operaciones del ciclo de vida de una pregunta.
 * Cada estado concreto implementa únicamente las transiciones permitidas
 * y rechaza las transiciones inválidas lanzando {@link IllegalStateException}.
 */
public interface QuestionState {

    /**
     * Envía la pregunta a revisión (Borrador -> Pendiente de revisión).
     */
    void submitForReview(Question question);

    /**
     * Aprueba la pregunta tras su evaluación (En revisión -> Aprobada).
     */
    void approve(Question question);

    /**
     * Publica la pregunta para su uso en exámenes (Aprobada -> Publicada).
     */
    void publish(Question question);

    /**
     * Archiva la pregunta (Publicada -> Archivada).
     */
    void archive(Question question);

    /**
     * Asigna un revisor a la pregunta (Pendiente de revisión -> En revisión).
     */
    default void assignReviewer(Question question) {
        throw new IllegalStateException("No se puede asignar revisor en el estado: " + getName());
    }

    /**
     * Rechaza la pregunta durante la revisión (En revisión -> Rechazada).
     */
    default void reject(Question question) {
        throw new IllegalStateException("No se puede rechazar la pregunta en el estado: " + getName());
    }

    /**
     * Retorna una pregunta rechazada a borrador para ajustes (Rechazada -> Borrador).
     */
    default void returnToDraft(Question question) {
        throw new IllegalStateException("No se puede retornar a borrador en el estado: " + getName());
    }

    /**
     * Indica si la pregunta está disponible para su inclusión activa en un examen o simulacro.
     * Solo retorna true en estado Publicada.
     */
    default boolean canBeUsedInExam() {
        return false;
    }

    /**
     * Retorna el nombre descriptivo del estado actual.
     */
    String getName();
}
