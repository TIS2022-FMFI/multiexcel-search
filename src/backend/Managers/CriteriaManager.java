package backend.Managers;

import backend.Models.Triple;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.function.Function;

public class CriteriaManager {

    private static Integer getPriority(String priority) {
        if (!priority.equals(""))
            return Integer.valueOf(priority);
        return Integer.MAX_VALUE;
    }

    private static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    private static <T extends Comparable<T>> Triple<T, T, Integer> getTriple(String fromText, String toText, String priority, Function<String, T> stringConverter, T minValue, T maxValue) {
        try {
            if (isNotEmpty(fromText) && isNotEmpty(toText)) {
                T from = stringConverter.apply(fromText);
                T to = stringConverter.apply(toText);
                if (from.compareTo(to) > 0)
                    throw new ValueException("From value must be less than or equal to the To value");
                else
                    return new Triple<>(from, to, getPriority(priority));
            } else if (isNotEmpty(toText)) {
                T to = stringConverter.apply(toText);
                return new Triple<>(minValue, to, getPriority(priority));
            } else if (isNotEmpty(fromText)) {
                T from = stringConverter.apply(fromText);
                return new Triple<>(from, maxValue, getPriority(priority));
            }
        } catch (NumberFormatException ignored) {
            throw new NumberFormatException("Value too large");
        }
        return null;
    }

    public static Triple<Double, Double, Integer> getDoubleTriple(String fromText, String toText, String priority) {
        return getTriple(fromText, toText, priority, Double::valueOf, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static Triple<Short, Short, Integer> getShortTriple(String fromText, String toText, String priority) {
        return getTriple(fromText, toText, priority, Short::valueOf, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static Triple<Integer, Integer, Integer> getIntegerTriple(String fromText, String toText, String priority) {
        return getTriple(fromText, toText, priority, Integer::valueOf, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


}
