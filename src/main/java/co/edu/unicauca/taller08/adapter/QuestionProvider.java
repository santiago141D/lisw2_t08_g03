package co.edu.unicauca.taller08.adapter;

import co.edu.unicauca.taller08.domain.Question;

/**
 * Interfaz objetivo (Target) esperada por el Banco de Preguntas Saber Pro
 * para la obtención e integración de preguntas.
 */
public interface QuestionProvider {

    /**
     * Retorna una instancia del modelo de dominio interno {@link Question}.
     */
    Question getQuestion();
}
