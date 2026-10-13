# Banco de Preguntas Saber Pro — Taller 08: Patrones de Diseño

Proyecto desarrollado para el curso **Laboratorio de Ingeniería de Software II (Periodo 2-2026)** de la **Universidad del Cauca (FIET)**.

---

## 🎯 Patrones de Diseño Implementados

En este taller se diseñaron e implementaron los cinco patrones requeridos de acuerdo a la guía académica:

1. **Patrón State (Comportamiento - 25%):**
   - Paquete: `co.edu.unicauca.taller08.state`
   - Modela el ciclo de vida de una pregunta a través de 7 estados: `DraftState` (Borrador), `PendingReviewState` (Pendiente de revisión), `ReviewState` (En revisión), `ApprovedState` (Aprobada), `RejectedState` (Rechazada), `PublishedState` (Publicada) y `ArchivedState` (Archivada).
   - Control estricto de transiciones polimórficas y validación de permisos (lanzando `IllegalStateException` en transiciones no permitidas).
   - Verificación de usabilidad en examen (`canBeUsedInExam()`, solo activo en estado Publicada).

2. **Patrón Template Method (Comportamiento - 25%):**
   - Paquete: `co.edu.unicauca.taller08.templatemethod`
   - Algoritmo invariante en la clase abstracta `QuestionEvaluator` con el método esqueleto `final evaluate(...)`.
   - Pasos abstractos `validate(...)` y `compareAnswers(...)` implementados en subclases concretas:
     - `SingleChoiceEvaluator` (Selección única con 1 respuesta).
     - `MultipleChoiceEvaluator` (Selección múltiple con coincidencia de conjuntos).
     - `TrueFalseEvaluator` (Verdadero / Falso con normalización booleana).

3. **Patrón Decorator (Estructural - 25%):**
   - Paquete: `co.edu.unicauca.taller08.decorator`
   - Interfaz base `QuestionComponent` y componente concreto `BasicQuestion`.
   - Decorador abstracto `QuestionDecorator`.
   - Decoradores concretos acumulables dinámicamente:
     - `HighDifficultyQuestion` (ponderación de puntaje x1.5 y sufijo de dificultad).
     - `ContextualizedQuestion` (asocia contexto o caso de estudio).
     - `FeedbackQuestion` (retroalimentación pedagógica).
     - `CompetencyQuestion` (asocia competencia evaluada en Saber Pro).
     - `MarkedForReviewQuestion` (marca para revisión técnica/docente).

4. **Patrón Adapter (Estructural - Requisito #9):**
   - Paquete: `co.edu.unicauca.taller08.adapter`
   - Integra preguntas provenientes de un servicio externo incompatible (`ExternalQuestionService` con formato JSON).
   - Implementa la interfaz esperada `QuestionProvider` a través de `ExternalQuestionAdapter`, transformando el formato JSON en entidades válidas de dominio `Question`.

5. **Patrón Facade (Estructural - 25%):**
   - Paquete: `co.edu.unicauca.taller08.facade`
   - Fachada unificada `QuestionBankFacade` que encapsula la interacción con los subsistemas internos:
     - `QuestionService`
     - `QuestionRepository`
     - `QuestionValidator`
     - `QuestionPublisher`
     - `QuestionImporter`
     - `QuestionEvaluator` (resolución automática de evaluador según el tipo).

---

## 📂 Estructura del Proyecto

Dentro de `src/main/java/co/edu/unicauca/` coexisten organizadamente el proyecto base y la entrega del Taller 08:

```
src/main/java/co/edu/unicauca/
├── lisw2_t02_g03/                                # Proyecto base (UI Swing, SQLite, Microkernel, Pipeline)
└── taller08/                                     # Taller 08: Patrones de Diseño
    ├── ApplicationMain.java                      # Demostración ejecutable de los 5 patrones
    ├── domain/
    │   └── Question.java                         # Entidad principal con integración State
    ├── state/                                    # PATRÓN STATE
    │   ├── QuestionState.java
    │   ├── DraftState.java
    │   ├── PendingReviewState.java
    │   ├── ReviewState.java
    │   ├── ApprovedState.java
    │   ├── RejectedState.java
    │   ├── PublishedState.java
    │   └── ArchivedState.java
    ├── templatemethod/                           # PATRÓN TEMPLATE METHOD
    │   ├── QuestionEvaluator.java
    │   ├── SingleChoiceEvaluator.java
    │   ├── MultipleChoiceEvaluator.java
    │   └── TrueFalseEvaluator.java
    ├── decorator/                                # PATRÓN DECORATOR
    │   ├── QuestionComponent.java
    │   ├── BasicQuestion.java
    │   ├── QuestionDecorator.java
    │   ├── HighDifficultyQuestion.java
    │   ├── ContextualizedQuestion.java
    │   ├── FeedbackQuestion.java
    │   ├── CompetencyQuestion.java
    │   └── MarkedForReviewQuestion.java
    ├── adapter/                                  # PATRÓN ADAPTER
    │   ├── ExternalQuestionService.java
    │   ├── QuestionProvider.java
    │   └── ExternalQuestionAdapter.java
    └── facade/                                   # PATRÓN FACADE
        ├── QuestionBankFacade.java
        ├── QuestionRepository.java
        ├── QuestionService.java
        ├── QuestionValidator.java
        ├── QuestionPublisher.java
        └── QuestionImporter.java
```

---

## 🚀 Ejecución de la Demostración

Para compilar y ejecutar la demostración interactiva por consola:

```bash
mvn compile exec:java -Dexec.mainClass="co.edu.unicauca.taller08.ApplicationMain"
```

O directamente mediante Java tras compilar:

```bash
java -cp target/classes co.edu.unicauca.taller08.ApplicationMain
```

---

## 🧪 Ejecución de Pruebas Automatizadas

La suite cuenta con **115 pruebas automatizadas en JUnit 5** pasando al 100% (85 preexistentes de `lisw2_t02_g03` + 30 pruebas unitarias nuevas para los 5 patrones en `taller08`):

```bash
mvn test
```

Para ejecutar pruebas individuales por patrón:
```bash
mvn test -Dtest=QuestionStateTest
mvn test -Dtest=QuestionEvaluatorTest
mvn test -Dtest=QuestionDecoratorTest
mvn test -Dtest=ExternalQuestionAdapterTest
mvn test -Dtest=QuestionBankFacadeTest
```
