package co.edu.unicauca.taller08.decorator;

/**
 * Decorador concreto para preguntas de alta dificultad.
 * Agrega el sufijo [ALTA DIFICULTAD] y aumenta la ponderación del puntaje.
 */
public class HighDifficultyQuestion extends QuestionDecorator {

    public HighDifficultyQuestion(QuestionComponent question) {
        super(question);
    }

    @Override
    public String getDescription() {
        return question.getDescription() + " [ALTA DIFICULTAD]";
    }

    @Override
    public double getScore() {
        return question.getScore() * 1.5;
    }
}
