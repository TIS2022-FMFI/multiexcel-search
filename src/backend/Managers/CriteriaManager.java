package backend.Managers;

import backend.Entities.Query;
import backend.Models.Criteria;
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

    private static <T extends Comparable<T>> boolean checkIfInRange(T value, T minValue, T maxValue) {
        return value.compareTo(minValue) > 0 && value.compareTo(maxValue) < 0;
    }

    private static boolean checkDecimal(Double value) {
        return checkIfInRange(value, -1000., 1000.);
    }


    private static boolean checkMediumInt(Integer value) {
        return checkIfInRange(value, -8388608, 8388607);
    }

    private static Double roundDecimal(Double value) {
        return Math.round(value * 100) / 100.;
    }

    private static boolean checkShort(Short value) {
        return checkIfInRange(value, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    // Ak to vie niekto lepsie tak mi to povedzte
    public static Query convertCriteriaToQuery(Criteria criteria) {
        Query query = new Query();

        if (criteria.getRubber() != null) {
            if (checkShort(criteria.getRubber().first))
                query.setRubber_from(criteria.getRubber().first);
            if (checkShort(criteria.getRubber().second))
                query.setRubber_to(criteria.getRubber().second);
        }

        if (criteria.getDiameter_AT() != null) {
            if (checkDecimal(criteria.getDiameter_AT().first))
                query.setDiameter_AT_from(roundDecimal(criteria.getDiameter_AT().first));
            if (checkDecimal(criteria.getDiameter_AT().second))
                query.setDiameter_AT_to(roundDecimal(criteria.getDiameter_AT().second));
        }

        if (criteria.getLength_L_AT() != null) {
            if (checkDecimal(criteria.getLength_L_AT().first))
                query.setLength_L_AT_from(roundDecimal(criteria.getLength_L_AT().first));
            if (checkDecimal(criteria.getLength_L_AT().second))
                query.setLength_L_AT_to(roundDecimal(criteria.getLength_L_AT().second));
        }

        if (criteria.getDiameter_IT() != null) {
            if (checkDecimal(criteria.getDiameter_IT().first))
                query.setDiameter_IT_from(roundDecimal(criteria.getDiameter_IT().first));
            if (checkDecimal(criteria.getDiameter_IT().second))
                query.setDiameter_IT_to(roundDecimal(criteria.getDiameter_IT().second));
        }

        if (criteria.getLength_L_IT() != null) {
            if (checkDecimal(criteria.getLength_L_IT().first))
                query.setLength_L_IT_from(roundDecimal(criteria.getLength_L_IT().first));
            if (checkDecimal(criteria.getLength_L_IT().second))
                query.setLength_L_IT_to(roundDecimal(criteria.getLength_L_IT().second));
        }

        if (criteria.getDiameter_ZT() != null) {
            if (checkDecimal(criteria.getDiameter_ZT().first))
                query.setDiameter_ZT_from(roundDecimal(criteria.getDiameter_ZT().first));
            if (checkDecimal(criteria.getDiameter_ZT().second))
                query.setDiameter_ZT_to(roundDecimal(criteria.getDiameter_ZT().second));
        }

        if (criteria.getLength_L_ZT() != null) {
            if (checkDecimal(criteria.getLength_L_ZT().first))
                query.setLength_L_ZT_from(roundDecimal(criteria.getLength_L_ZT().first));
            if (checkDecimal(criteria.getLength_L_ZT().second))
                query.setLength_L_ZT_to(roundDecimal(criteria.getLength_L_ZT().second));
        }

        if (criteria.getCr_steg() != null) {
            if (checkMediumInt(criteria.getCr_steg().first))
                query.setCr_steg_from(criteria.getCr_steg().first);
            if (checkMediumInt(criteria.getCr_steg().second))
                query.setCr_steg_to(criteria.getCr_steg().second);
        }
        if (criteria.getCr_niere() != null) {
            if (checkShort(criteria.getCr_niere().first))
                query.setCr_niere_from(criteria.getCr_niere().first);
            if (checkShort(criteria.getCr_niere().second))
                query.setCr_niere_to(criteria.getCr_niere().second);
        }

        if (criteria.getCa() != null) {
            if (checkShort(criteria.getCa().first))
                query.setCa_from(criteria.getCa().first);
            if (checkShort(criteria.getCa().second))
                query.setCa_to(criteria.getCa().second);
        }

        if (criteria.getCt() != null) {
            if (checkDecimal(criteria.getCt().first))
                query.setCt_from(roundDecimal(criteria.getCt().first));
            if (checkDecimal(criteria.getCt().second))
                query.setCt_to(roundDecimal(criteria.getCt().second));
        }

        if (criteria.getCk() != null) {
            if (checkDecimal(criteria.getCk().first))
                query.setCk_from(roundDecimal(criteria.getCk().first));
            if (checkDecimal(criteria.getCk().second))
                query.setCk_to(roundDecimal(criteria.getCk().second));
        }

        return query;
    }

}
