package co.edu.unicauca.taller08.decorator;

/**
 * Interfaz base del patrón Decorator para preguntas.
 * Permite obtener dinámicamente la descripción enriquecida y el puntaje ponderado.
 */
public interface QuestionComponent {

    /**
     * Retorna la descripción del enunciado de la pregunta, incluyendo
     * las etiquetas aplicadas por los decoradores.
     */
    String getDescription();

    /**
     * Retorna el puntaje o ponderación de la pregunta, ajustado por los decoradores.
     */
    double getScore();
}
