    package com.todoapp.demo.domain.usecase;

    import com.todoapp.demo.domain.Status;
    import com.todoapp.demo.domain.api.ITaskServicePort;
    import com.todoapp.demo.domain.model.Task;
    import com.todoapp.demo.domain.model.User;
    import com.todoapp.demo.domain.spi.ITaskPersistencePort;

    import java.time.LocalDate;
    import java.util.List;

    public class TaskUseCase implements ITaskServicePort{

        private final ITaskPersistencePort taskPersistencePort;

        public TaskUseCase(ITaskPersistencePort taskPersistencePort) {
            this.taskPersistencePort = taskPersistencePort;
        }

        @Override
        public void createTask(Task task, User userCreator, User userToAssign) {

        }

        @Override
        public void updateTask(Task task, User updaterUser) {

        }

        @Override
        public void deleteTask(Long taskId, User deleterUser) {

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

        
    }
