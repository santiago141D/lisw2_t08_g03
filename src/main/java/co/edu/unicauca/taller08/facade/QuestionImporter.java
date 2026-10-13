package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.adapter.QuestionProvider;
import co.edu.unicauca.taller08.domain.Question;

/**
 * Subsistema encargado de la importación de preguntas desde fuentes externas.
 */
public class QuestionImporter {

    public Question importQuestion(QuestionProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("El proveedor de preguntas externas no puede ser nulo.");
        }
        return provider.getQuestion();
    }
}
