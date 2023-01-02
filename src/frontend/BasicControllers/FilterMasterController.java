package frontend.BasicControllers;

import backend.Models.Filterable;

import java.util.List;

public interface FilterMasterController {

    default <T> Class<?> getClassOfList(List<T> list) {
        if (list != null && !list.isEmpty())
            return list.get(0).getClass();
        return null;
    }

    void setParameters(List<? extends Filterable> parameters);

    List<? extends Filterable> getParameters(Class<?> type);
}
