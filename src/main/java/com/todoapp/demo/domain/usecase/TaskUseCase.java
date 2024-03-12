    package com.todoapp.demo.domain.usecase;

    import com.todoapp.demo.domain.Status;
    import com.todoapp.demo.domain.api.ITaskServicePort;
    import com.todoapp.demo.domain.exception.ErrorMessages;
    import com.todoapp.demo.domain.exception.TaskValidationException;
    import com.todoapp.demo.domain.model.Task;
    import com.todoapp.demo.domain.model.User;
    import com.todoapp.demo.domain.spi.ITaskPersistencePort;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.regex.Pattern;

    public class TaskUseCase implements ITaskServicePort{

        private final ITaskPersistencePort taskPersistencePort;

        public TaskUseCase(ITaskPersistencePort taskPersistencePort) {
            this.taskPersistencePort = taskPersistencePort;
        }

        @Override
        public void createTask(Task task) {

            if (!isValidTitle(task.getTitle())){
                throw new TaskValidationException(ErrorMessages.TITLE_INVALID.getMessage());
            }
            if(!isValidDescription(task.getDescription())){
                throw new TaskValidationException(ErrorMessages.DESCRIPTION_INVALID.getMessage());
            }
            if (!isValidStartDate(task.getStartDate())){
                throw new TaskValidationException(ErrorMessages.STARTDATE_INVALID.getMessage());
            }
            if(!isValidFinishDate(task.getFinishDate())){
                throw new TaskValidationException(ErrorMessages.FINISHDATE_INVALID.getMessage());
            }
            if(!isValidStatus(task.getStatus().name())){
                throw new TaskValidationException((ErrorMessages.STATUS_INVALID.getMessage()));
            }

            taskPersistencePort.createTask(task);
        }

        @Override
        public void updateTask(Task task) {

        }

        @Override
        public void deleteTask(Long taskId) {

        }

        @Override
        public Task getTaskById(Long taskId) {
            return null;
        }

        @Override
        public List<Task> getAllTasks() {
            return null;
        }

        @Override
        public List<Task> getTasksByUser(String userId) {
            return null;
        }

        @Override
        public List<Task> getTaskByStatus(String status) {
            return null;
        }

        @Override
        public List<Task> getTaskByStartDate(LocalDate date) {
            return null;
        }

        @Override
        public List<Task> getTaskByFinishDate(LocalDate date) {
            return null;
        }

        @Override
        public void updateTaskStatus(Long taskId, String userId, String status) {

        }

        //VALIDACIONES
        public boolean isValidIdTask(Long idTask){
            if(idTask == null || idTask.toString().isEmpty() ){
                return false;
            }
            return true;
        }

        public boolean isValidTitle(String title){
            if (title == null || title.isEmpty()){
                return false;
            }
            return true;
        }

        public boolean isValidDescription(String description){
            if (description == null || description.isEmpty()){
                return false;
            }
            String regex = "^[A-Za-z]+$";

            return description.matches(regex);
        }

        public boolean isValidStartDate(LocalDate startDate){
            if (startDate == null || startDate.toString().isEmpty()){
                return false;
            }
            return true;
        }

        public boolean isValidFinishDate(LocalDate finishDate){
            if (finishDate == null || finishDate.toString().isEmpty()){
                return false;
            }
            return true;
        }
        //String fechaString = "2024-03-05";
        //LocalDate fecha = LocalDate.parse(fechaString);

        public boolean isValidStatus(String status) {

            try {
                Status[] validStatus = Status.values();

                for (Status valStatus : validStatus) {
                    if (valStatus.name().equalsIgnoreCase(status)) {
                        return true; //El valor coincide con un status válido
                    }
                }

                return false;

            } catch (IllegalArgumentException e) {
                //Capturar excepción si el valor no coincide con ningún enum válido
                return false;
            }
        }

        public boolean isValidHistoryPoints(Integer historyPoints) {
            // Verificar si el número es nulo
            if (historyPoints == null){
                return false;
            }
            // Verificar si el número se ajusta a la expresión regular (solo caracteres del 1 al 9 sin caracteres especiales)
            String numberString = String.valueOf(historyPoints);
            String regex = "[1-9]+"; // La expresión regular solo permite caracteres del 1 al 9 sin caracteres especiales
            return Pattern.matches(regex, numberString);
        }

        public boolean isValidIdUser(String idUser) {
            if (idUser == null || idUser.isEmpty()) {
                return false;
            }
            // Usamos una expresión regular para verificar que el número contenga solo números y tenga máximo 10 dígitos
            String regex = "\\d{1,10}";
            return idUser.matches(regex);
        }
    }
