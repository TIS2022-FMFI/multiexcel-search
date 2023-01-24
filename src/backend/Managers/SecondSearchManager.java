package backend.Managers;

import backend.Entities.Category;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SecondSearchManager {

    private static List<Pair<String, Integer>> orders;

    /**
     * Filters parts based on match with categories
     *
     * @param parts      list of parts to filter
     * @param categories selected categories to filter by
     * @return list of filtered parts
     */
    public static List<Part> filterByCategories(List<Part> parts, List<Category> categories) {
        List<Integer> categoriesId = categories.stream().map(Category::getCategory_id).collect(Collectors.toList());

        return parts.stream().filter(x -> categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());
    }

    /**
     * Orders parts based on rating and match with selected categories
     *
     * @param parts      parts to order
     * @param categories categories to order by
     * @return ordered parts
     */
    public static List<Part> sortByRating(List<Part> parts, List<Category> categories) {
        List<Integer> categoriesId = categories.stream().map(Category::getCategory_id).collect(Collectors.toList());
        List<Part> matchingCategories = parts.stream().filter(x -> categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());
        List<Part> notMatchingCategories = parts.stream().filter(x -> !categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());

        RatingComparator ratingComparator = new RatingComparator();
        matchingCategories.sort(ratingComparator);
        notMatchingCategories.sort(ratingComparator);

        matchingCategories.addAll(notMatchingCategories);
        return matchingCategories;
    }

    /**
     * Orders parts based on priority and match with selected categories
     *
     * @param parts      parts to order
     * @param categories categories to order by
     * @param criteria   criteria with priorities to order by
     * @return ordered parts
     */
    public static List<Part> sortByPriority(List<Part> parts, List<Category> categories, Criteria criteria) {
        List<Integer> categoriesId = categories.stream().map(Category::getCategory_id).collect(Collectors.toList());
        List<Part> matchingCategories = parts.stream().filter(x -> categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());
        List<Part> notMatchingCategories = parts.stream().filter(x -> !categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());

        try {
            orders = new ArrayList<>();

            BeanInfo beanInfo = Introspector.getBeanInfo(Criteria.class);
            for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
                try {
                    String propertyName = propertyDesc.getName();

                    if (Objects.equals(propertyName, "partsName") || Objects.equals(propertyName, "customers"))
                        continue;
                    if (BeanUtils.getProperty(criteria, propertyName) != null) {
                        Triple<Object, Object, Integer> property = (Triple<Object, Object, Integer>) PropertyUtils.getProperty(criteria, propertyName);
                        orders.add(new Pair<>(propertyName, property.third));
                    }
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {
                }
            }
            orders.sort(Comparator.comparingInt(Pair::getValue));

            PriorityComparator priorityComparator = new PriorityComparator();
            matchingCategories.sort(priorityComparator);
            notMatchingCategories.sort(priorityComparator);

            matchingCategories.addAll(notMatchingCategories);
        } catch (IntrospectionException e) {
            return null;
        }
        return matchingCategories;
    }

    private static class RatingComparator implements Comparator<Part> {

        @Override
        public int compare(Part o1, Part o2) {
            if (Objects.equals(o1.getRating(), o2.getRating()))
                return Integer.compare(o1.getRating(), o2.getRating());
            return Integer.compare(o1.getInternal_rating(), o2.getInternal_rating());
        }
    }

    private static class PriorityComparator implements Comparator<Part> {

        @Override
        public int compare(Part o1, Part o2) {
            for (Pair<String, Integer> order : orders) {
                try {
                    Object val1 = PropertyUtils.getProperty(o1, order.getKey());
                    Object val2 = PropertyUtils.getProperty(o2, order.getKey());
                    if (!Objects.equals(val1, val2)) {
                        if (val1 instanceof Integer) {
                            return Integer.compare((Integer) val1, (Integer) val2);
                        }
                        if (val1 instanceof Double) {
                            return Double.compare((Double) val1, (Double) val2);
                        }
                        if (val1 instanceof Short) {
                            return Double.compare((Short) val1, (Short) val2);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
            }
            return 0;
        }
    }
}
