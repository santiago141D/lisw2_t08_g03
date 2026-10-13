package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Subsistema encargado de la publicación oficial de preguntas aprobadas.
 */
public class QuestionPublisher {

    public void publish(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta a publicar no puede ser nula.");
        }
        question.publish();
    }
}
