package backend.Managers;

import backend.Entities.Part;
import backend.Models.Criteria;
import backend.Models.Triple;
import backend.Sessions.DBS;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.scene.control.Alert;
import javafx.util.Pair;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FirstSearchManager {
    private static Double check(Double d) {
        return d == 0 ? null : d;
    }

    private static Short check(Short s) {
        return s == 0 ? null : s;
    }

    private static Integer check(Integer i) {
        return i == 0 ? null : i;
    }

    public static List<Part> search(Criteria criteria) {
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM parts p JOIN part_names pn ON p.part_name_id = pn.part_name_id JOIN customers c ON p.customer_id = c.customer_id");
            List<String> subStatements = new ArrayList<>();
            List<Object> statementValues = new ArrayList<>();
            List<Pair<String, Integer>> orders = new ArrayList<>();

            if (criteria.getPartNamesStrings() != null) {
                subStatements.add("FIND_IN_SET( pn.part_name, ? ) > 0");
                statementValues.add(String.join(", ", criteria.getPartNamesStrings()));
            }

            if (criteria.getCustomersStrings() != null) {
                subStatements.add("FIND_IN_SET( c.customer_name, ? ) > 0");
                statementValues.add(String.join(", ", criteria.getCustomersStrings()));
            }

            BeanInfo beanInfo = Introspector.getBeanInfo(Criteria.class);
            for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
                String propertyName = propertyDesc.getName();
                try {
                    if (Arrays.asList("partNames", "partNamesStrings", "customers", "customersStrings").contains(propertyName))
                        continue;
                    if (BeanUtils.getProperty(criteria, propertyName) != null) {
                        Triple<Object, Object, Integer> property = (Triple<Object, Object, Integer>) PropertyUtils.getProperty(criteria, propertyName);
                        subStatements.add("p." + propertyName + " BETWEEN ? AND ?");
                        statementValues.add(property.first);
                        statementValues.add(property.second);
                        orders.add(new Pair<>("p." + propertyName, property.third));
                    }
                } catch (IllegalAccessException | InvocationTargetException  e) {
                    MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.getMessage());
                } catch (NoSuchMethodException ignored){}
            }

            if (!subStatements.isEmpty()) {
                statement.append(" WHERE ");
                statement.append(String.join(" AND ", subStatements));
            }

            if (!orders.isEmpty()) {
                statement.append(" ORDER BY");
                orders.sort(Comparator.comparingInt(Pair::getValue));
                for (Pair<String, Integer> order : orders) {
                    statement.append(" ").append(order.getKey()).append(" DESC");
                    if (order != orders.get(orders.size() - 1))
                        statement.append(",");
                }
            }

            PreparedStatement s = DBS.getConnection().prepareStatement(statement.toString());
            int i = 1;
            for (Object value : statementValues) {
                s.setObject(i, value);
                i++;
            }

            ResultSet r = s.executeQuery();
            List<Part> parts = new ArrayList<>();
            while (r.next()) {
                Part part = new Part();
                part.setPart_number(r.getString("part_number"));
                part.setCustomer_id((BigInteger) r.getObject("customer_id"));
                part.setPart_name_id((BigInteger) r.getObject("part_name_id"));
                part.setCategory_id((BigInteger) r.getObject("category_id"));
                part.setDrawing_id((BigInteger) r.getObject("drawing_id"));
                part.setRubber(check(r.getShort("rubber")));
                part.setDiameter_AT(check(r.getDouble("diameter_AT")));
                part.setDiameter_AT_tol(r.getString("diameter_AT_tol"));
                part.setLength_L_AT(check(r.getDouble("length_L_AT")));
                part.setLength_L_AT_tol(r.getString("length_L_AT_tol"));
                part.setDiameter_IT(check(r.getDouble("diameter_IT")));
                part.setDiameter_IT_tol(r.getString("diameter_IT_tol"));
                part.setLength_L_IT(check(r.getDouble("length_L_IT")));
                part.setLength_L_IT_tol(r.getString("length_L_IT_tol"));
                part.setDiameter_ZT(check(r.getDouble("diameter_ZT")));
                part.setDiameter_ZT_tol(r.getString("diameter_ZT_tol"));
                part.setLength_L_ZT(check(r.getDouble("length_L_ZT")));
                part.setLength_L_ZT_tol(r.getString("length_L_ZT_tol"));
                part.setCr_steg(check(r.getInt("cr_steg")));
                part.setCr_niere(check(r.getShort("cr_niere")));
                part.setCa(check(r.getShort("ca")));
                part.setCt(check(r.getDouble("ct")));
                part.setCk(check(r.getDouble("ck")));
                part.setRating(check(r.getInt("rating")));

                parts.add(part);
            }
            return parts;

        } catch (SQLException e) {
            return null;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
