package backend.Managers;

import backend.Entities.*;
import backend.Models.Criteria;
import backend.Sessions.DBS;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.Pair;

import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class HistoryManager {

    private static boolean countOnly = false;

    private static Double check(Double d) {
        return d == 0 ? null : d;
    }

    private static Short check(Short s) {
        return s == 0 ? null : s;
    }

    private static Integer check(Integer i) {
        return i == 0 ? null : i;
    }

    /**
     * Add search and selected parts to History
     *
     * @param criteria criteria by which the first search was commenced
     * @param parts    selected parts
     */
    public static void saveSearchToHistory(Criteria criteria, List<Part> parts) {
        try {
            DBS.getConnection().setAutoCommit(false);

            Query query = CriteriaManager.convertCriteriaToQuery(criteria);

            query.setUser_id(SESSION.getSession().getUser_id());

            query.setDate(Timestamp.valueOf(LocalDateTime.now()));

            query.insert();

            for (Part part : parts) {
                Part_query partQuery = new Part_query();
                partQuery.setPart_number(part.getPart_number());
                partQuery.setQuery_id(BigInteger.valueOf(query.getQuery_id()));
                partQuery.insert();
            }

            if (criteria.getCategories() != null) {
                for (Category category : criteria.getCategories()) {
                    Category_query categoryQuery = new Category_query();
                    categoryQuery.setCategory_id(BigInteger.valueOf(category.getCategory_id()));
                    categoryQuery.setQuery_id(BigInteger.valueOf(query.getQuery_id()));
                    categoryQuery.insert();
                }
            }

            DBS.getConnection().commit();
            DBS.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            try {
                DBS.getConnection().rollback();
                DBS.getConnection().setAutoCommit(true);
            } catch (SQLException ignored1) {
            }
        }
    }

    private static String createQueryString(List<Category> categories, Pair<Date, Date> dateFromTo, List<User> users) {

        if (dateFromTo == null && users == null && categories == null) {
            if (countOnly) {
                return "SELECT COUNT(date) as query_total_count FROM multiexcel.queries ";
            } else {
                return "SELECT * FROM multiexcel.queries ";
            }
        }

        boolean queryUsers = false;
        boolean queryDate = false;
        boolean queryCategories = false;

        if (categories != null) {
            if (!categories.isEmpty()) {
                queryCategories = true;
            }
        }

        if (users != null) {
            if (!users.isEmpty()) {
                queryUsers = true;
            }
        }

        if (dateFromTo != null) {
            if (dateFromTo.getKey() != null && dateFromTo.getValue() != null) {
                queryDate = true;
            }
        }

        StringBuilder queryString = new StringBuilder();
        String columnUserId = "user_id=";
        String columnCategoryId = "category_id=";
        String prefix = "";

        boolean isFirst = true;
        if (queryCategories) {
            if (countOnly) {
                prefix = "SELECT COUNT(date) as query_total_count FROM multiexcel.categories_queries JOIN multiexcel.queries q on q.query_id = categories_queries.query_id ";
            } else {
                prefix = "SELECT * FROM multiexcel.categories_queries JOIN multiexcel.queries q on q.query_id = categories_queries.query_id ";
            }
            if (queryString.length() == 0) {
                queryString.append("WHERE ");
            } else {
                queryString.append("AND ");
            }
            for (Category c : categories) {
                if (isFirst) {
                    queryString.append("(");
                    queryString.append(columnCategoryId).append(c.getCategory_id());
                    isFirst = false;
                } else {
                    queryString.append(" OR ").append(columnCategoryId).append(c.getCategory_id());
                }
            }
            queryString.append(") ");
        } else {
            if (countOnly) {
                prefix = "SELECT COUNT(date) as query_total_count FROM multiexcel.queries ";
            } else {
                prefix = "SELECT * FROM multiexcel.queries ";
            }
        }

        if (queryDate) {
            if (queryString.length() == 0) {
                queryString.append("WHERE ");
            } else {
                queryString.append("AND ");
            }
            queryString.append(String.format("(date <= '%s' AND date >= '%s') ", dateFromTo.getValue(), dateFromTo.getKey()));
        }

        isFirst = true;
        if (queryUsers) {
            if (queryString.length() == 0) {
                queryString.append("WHERE ");
            } else {
                queryString.append("AND ");
            }
            for (User u : users) {
                if (isFirst) {
                    queryString.append("(");
                    queryString.append(columnUserId).append(u.getUser_id());
                    isFirst = false;
                } else {
                    queryString.append(" OR ").append(columnUserId).append(u.getUser_id());
                }
            }
            queryString.append(") ");
        }
        return prefix + queryString;
    }

    /**
     * get list of queries with selected filters
     *
     * @param dateFromTo     Date from and Date to (keep the order)
     * @param users          list of users to filter by (if none pass null or empty list)
     * @param queriesPerPage number of items to show per page
     * @param pageIndex      page number (starting from 0 included)
     * @return List of queries if succeeded otherwise returns null
     */
    public static ObservableList<Query> getQueriesWithFilters(List<Category> categories, Pair<Date, Date> dateFromTo, List<User> users, int queriesPerPage, int pageIndex) {
        countOnly = false;
        int itemsPerPage;
        int pageNumber;
        itemsPerPage = Math.max(queriesPerPage, 0);
        pageNumber = Math.max(pageIndex, 0);


        String sqlString = "";
        sqlString += createQueryString(categories, dateFromTo, users);

        sqlString += String.format("ORDER BY date DESC LIMIT %d, %d", pageNumber * itemsPerPage, itemsPerPage);
        //sqlString+= "ORDER BY date ASC LIMIT " + pageNumber * itemsPerPage + ", " + itemsPerPage;
        //System.out.println("Filtered List Query: " + sqlString);
        try (
                PreparedStatement s = DBS.getConnection().prepareStatement(sqlString);
                ResultSet r = s.executeQuery()
        ) {
            ObservableList<Query> queries = FXCollections.observableArrayList();
            while (r.next()) {
                Query q = new Query();
                q.setQuery_id(r.getInt("query_id"));
                q.setUser_id(r.getInt("user_id"));

                q.setRubber_from(check(r.getShort("rubber_from")));
                q.setDiameter_AT_from(check(r.getDouble("diameter_AT_from")));
                q.setLength_L_AT_from(check(r.getDouble("length_L_AT_from")));
                q.setDiameter_IT_from(check(r.getDouble("diameter_IT_from")));
                q.setLength_L_IT_from(check(r.getDouble("length_L_IT_from")));
                q.setDiameter_ZT_from(check(r.getDouble("diameter_ZT_from")));
                q.setLength_L_ZT_from(check(r.getDouble("length_L_ZT_from")));
                q.setCr_steg_from(check(r.getInt("cr_steg_from")));
                q.setCr_niere_from(check(r.getShort("cr_niere_from")));
                q.setCa_from(check(r.getShort("ca_from")));
                q.setCt_from(check(r.getDouble("ct_from")));
                q.setCk_from(check(r.getDouble("ck_from")));

                q.setDate(r.getTimestamp("date"));

                q.setRubber_to(check(r.getShort("rubber_to")));
                q.setDiameter_AT_to(check(r.getDouble("diameter_AT_to")));
                q.setLength_L_AT_to(check(r.getDouble("length_L_AT_to")));
                q.setDiameter_IT_to(check(r.getDouble("diameter_IT_to")));
                q.setLength_L_IT_to(check(r.getDouble("length_L_IT_to")));
                q.setDiameter_ZT_to(check(r.getDouble("diameter_ZT_to")));
                q.setLength_L_ZT_to(check(r.getDouble("length_L_ZT_to")));
                q.setCr_steg_to(check(r.getInt("cr_steg_to")));
                q.setCr_niere_to(check(r.getShort("cr_niere_to")));
                q.setCa_to(check(r.getShort("ca_to")));
                q.setCt_to(check(r.getDouble("ct_to")));
                q.setCk_to(check(r.getDouble("ck_to")));

                queries.add(q);
            }
            return queries;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }

    /**
     * gets total number of rows in Query table (i.e. item count)
     *
     * @return Integer if successful otherwise -1
     */
    public static int getTotalNumberOfQueriesWithFilters(List<Category> categories, Pair<Date, Date> dateFromTo, List<User> users) {
        countOnly = true;
        String sqlString = "";
        sqlString += createQueryString(categories, dateFromTo, users);

        //System.out.println("Filtered Count Query: " + sqlString);
        try (
                PreparedStatement s = DBS.getConnection().prepareStatement(sqlString);
                ResultSet r = s.executeQuery()
        ) {
            if (r.next()) {
                return r.getInt("query_total_count");
            }
            return 0;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return -1;
        }
    }
}
