package backend.Managers;

import backend.Entities.Part;
import backend.Models.Criteria;
import backend.Models.Triple;
import javafx.util.Pair;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SecondSearchManager {

    private static List<Pair<String, Integer>> orders;

    /**
     * Orders parts based on rating
     *
     * @param parts parts to order
     */
    public static void sortByRating(List<Part> parts) {
        parts.sort(new RatingComparator());
    }

    /**
     * Orders parts based on priority
     *
     * @param parts    parts to order
     * @param criteria criteria with priorities to order by
     */
    public static void sortByPriority(List<Part> parts, Criteria criteria) {
        try {
            orders = new ArrayList<>();

            BeanInfo beanInfo = Introspector.getBeanInfo(Criteria.class);
            for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
                try {
                    String propertyName = propertyDesc.getName();

                    if (Arrays.asList("partNames", "partNamesStrings", "customers", "customersStrings", "categories", "categoriesStrings").contains(propertyName))
                        continue;
                    if (BeanUtils.getProperty(criteria, propertyName) != null) {
                        Triple<Object, Object, Integer> property = (Triple<Object, Object, Integer>) PropertyUtils.getProperty(criteria, propertyName);
                        if (property.third != Integer.MAX_VALUE)
                            orders.add(new Pair<>(propertyName, property.third));
                    }
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {
                }
            }
            orders.sort(Comparator.comparingInt(Pair::getValue));

            PriorityComparator priorityComparator = new PriorityComparator();
            parts.sort(priorityComparator);

        } catch (IntrospectionException ignored) {
        }
    }

    /**
     * Compares ratings of two parts
     * Descending order
     * rating - desc, internal rating - asc
     */
    private static class RatingComparator implements Comparator<Part> {
        @Override
        public int compare(Part o1, Part o2) {
            if (!Objects.equals(o1.getRating(), o2.getRating()))
                return Integer.compare(o2.getRating(), o1.getRating());
            return Integer.compare(o1.getInternal_rating(), o2.getInternal_rating());
        }
    }

    /**
     * Compares priorities of two parts
     * Descending order
     */
    private static class PriorityComparator implements Comparator<Part> {
        @Override
        public int compare(Part o1, Part o2) {
            for (Pair<String, Integer> order : orders) {
                try {
                    Object val1 = PropertyUtils.getProperty(o1, order.getKey());
                    Object val2 = PropertyUtils.getProperty(o2, order.getKey());
                    if(Objects.equals(val1, val2))
                        return 0;
                    else if(val2 != null && val1 == null)
                        return 1;
                    else if(val2 == null)
                        return -1;
                    else {
                        if (val1 instanceof Integer) {
                            return Integer.compare((Integer) val2, (Integer) val1);
                        }
                        if (val1 instanceof Double) {
                            return Double.compare((Double) val2, (Double) val1);
                        }
                        if (val1 instanceof Short) {
                            return Double.compare((Short) val2, (Short) val1);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
            }
            return 0;
        }
    }
}
