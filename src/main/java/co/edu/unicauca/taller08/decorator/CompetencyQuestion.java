package co.edu.unicauca.taller08.decorator;

/**
 * Decorador concreto para preguntas vinculadas a una competencia específica evaluada en Saber Pro
 * (por ejemplo: Razonamiento Cuantitativo, Lectura Crítica, Competencias Ciudadanas, etc.).
 */
public class CompetencyQuestion extends QuestionDecorator {

    private final String competency;

    public CompetencyQuestion(QuestionComponent question, String competency) {
        super(question);
        this.competency = competency != null ? competency.trim() : "";
    }

    @Override
    public String getDescription() {
        return question.getDescription() + " [COMPETENCIA: " + competency + "]";
    }
}
