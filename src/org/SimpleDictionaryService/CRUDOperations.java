package org.SimpleDictionaryService;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public enum CRUDOperations{

    CREATE  ((Creating)(int firstValue, int secondValue) -> {
        return firstValue + secondValue;
    }){
        @Override
        public Object execute(Object... args) throws NoSuchFieldException{
            Class<?> cls = Reading.class;
            return ((Creating)this.currentOperation).execute(((int) args[0]), ((int) args[1]));
        }
    },
    READ    ((Reading)(String strValue) -> {
        return "READ : " + strValue;
    }){
        @Override
        public Object execute(Object... args) {
            return ((Reading)currentOperation).execute(((String) args[0]));
        }
    },
    UPDATE  ((Updating)(int firstValue) -> {
        return firstValue + 1;
    }){
        @Override
        public Object execute(Object... args) {
            return ((Updating)currentOperation).execute(((int) args[0]));
        }
    },
    DELETE  ((Deleting)(int firstValue) -> {
        return firstValue - 2;
    }){
        @Override
        public Object execute(Object... args) {
            return ((Deleting)currentOperation).execute(((int) args[0]));
        }
    };

    @FunctionalInterface
    public interface Creating extends CRUDExecutable{
        int execute(int firstValue, int secondValue);
    }

    @FunctionalInterface
    public interface Reading extends CRUDExecutable{
        String execute(String strValue);
    }

    @FunctionalInterface
    public interface Updating extends CRUDExecutable{
        int execute(int firstValue);
    }

    @FunctionalInterface
    public interface Deleting extends CRUDExecutable{
        int execute(int firstValue);
    }

    protected final CRUDExecutable currentOperation;

    CRUDOperations(final CRUDExecutable operation){
        this.currentOperation = operation;
    }

    public abstract Object execute(Object... args) throws NoSuchFieldException;

//    public Object execute(Object... args){
//        switch (this){
//            case CREATE:
//                return ((Creating)currentOperation).execute(((int) args[0]), ((int) args[1]));
//            case READ:
//                return ((Reading)currentOperation).execute(((String) args[0]));
//            case UPDATE:
//                return ((Updating)currentOperation).execute(((int) args[0]));
//            case DELETE:
//                return ((Deleting)currentOperation).execute(((int) args[0]));
//            default:
//                return null;
//        }
//    }
}
