package backend.Managers;

import backend.Entities.Query;
import backend.Entities.User;
import backend.DBS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    /**
     * remove query from database
     *
     * @param query query
     *
     * @return True if succeeded otherwise False
     */
    public static boolean removeQueryFromHistory(Query query){
        try {
            query.delete();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * add new query to database
     *
     * @param query query
     *
     * @return True if succeeded otherwise False
     */
    public static boolean addQueryToHistory(Query query){
        try {
            query.insert();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private String CreateQueryString(Pair<Date, Date> dateFromTo, List<User> users){
        if(dateFromTo == null && users == null){
            return "";
        }

        boolean queryUsers = false;
        boolean queryDate = false;

        if (users != null){
            if (!users.isEmpty()){
                queryUsers = true;
            }
        }

        if(dateFromTo != null){
            if(dateFromTo.getKey() != null && dateFromTo.getValue() != null){
                queryDate = true;
            }
        }

        StringBuilder queryString = new StringBuilder();
        String columnUserId = "user_id=";

        boolean isFirst = true;
        if(queryDate){
            if (queryString.length() == 0){
                queryString.append("WHERE ");
            }else{
                queryString.append("AND ");
            }
            queryString.append(String.format("(date <= '%s' AND date >= '%s') ", dateFromTo.getValue(), dateFromTo.getKey()));
        }

        isFirst = true;
        if (queryUsers){
            if (queryString.length() == 0){
                queryString.append("WHERE ");
            }else{
                queryString.append("AND ");
            }
            for (User u : users) {
                if (isFirst){
                    queryString.append("(");
                    queryString.append(columnUserId).append(u.getUser_id());
                    isFirst = false;
                }else{
                    queryString.append(" OR ").append(columnUserId).append(u.getUser_id());
                }
            }
            queryString.append(") ");
        }

        return queryString.toString();
    }

    /**
     * get list of queries with selected filters
     *
     * @param dateFromTo Date from and Date to (keep the order)
     * @param users list of users to filter by (if none pass null or empty list)
     * @param queriesPerPage number of items to show per page
     * @param pageIndex page number (starting from 0 included)
     *
     * @return List of queries if succeeded otherwise returns null
     */
    public static ObservableList<Query> getQueriesWithFilters(Pair<Date, Date> dateFromTo, List<User> users, int queriesPerPage, int pageIndex){

        int itemsPerPage;
        int pageNumber;
        itemsPerPage = Math.max(queriesPerPage, 0);
        pageNumber = Math.max(pageIndex, 0);
        
        
        String sqlString = "SELECT * FROM multiexcel.queries ";
        sqlString += (new HistoryManager()).CreateQueryString(dateFromTo, users);

        sqlString += String.format("ORDER BY date DESC LIMIT %d, %d",pageNumber * itemsPerPage, itemsPerPage);
        //sqlString+= "ORDER BY date ASC LIMIT " + pageNumber * itemsPerPage + ", " + itemsPerPage;
        System.out.println("Filtered List Query: " + sqlString);
        try(
                PreparedStatement s = DBS.getConnection().prepareStatement(sqlString);
                ResultSet r = s.executeQuery()
        ){
            ObservableList<Query> queries = FXCollections.observableArrayList();
            while (r.next()){
                Query q = new Query();
                q.setQuery_id(r.getInt("query_id"));
                q.setUser_id(r.getInt("user_id"));

                q.setRubber_from(r.getShort("rubber_from"));
                q.setDiameter_AT_from(r.getDouble("diameter_AT_from"));
                q.setLength_L_AT_from(r.getDouble("length_L_AT_from"));
                q.setDiameter_IT_from(r.getDouble("diameter_IT_from"));
                q.setLength_L_IT_from(r.getDouble("length_L_IT_from"));
                q.setDiameter_ZT_from(r.getDouble("diameter_ZT_from"));
                q.setLength_L_ZT_from(r.getDouble("length_L_ZT_from"));
                q.setCr_steg_from(r.getInt("cr_steg_from"));
                q.setCr_niere_from(r.getShort("cr_niere_from"));
                q.setCa_from(r.getShort("ca_from"));
                q.setCt_from(r.getDouble("ct_from"));
                q.setCk_from(r.getDouble("ck_from"));

                q.setDate(r.getDate("date"));

                q.setRubber_to(r.getShort("rubber_to"));
                q.setDiameter_AT_to(r.getDouble("diameter_AT_to"));
                q.setLength_L_AT_to(r.getDouble("length_L_AT_to"));
                q.setDiameter_IT_to(r.getDouble("diameter_IT_to"));
                q.setLength_L_IT_to(r.getDouble("length_L_IT_to"));
                q.setDiameter_ZT_to(r.getDouble("diameter_ZT_to"));
                q.setLength_L_ZT_to(r.getDouble("length_L_ZT_to"));
                q.setCr_steg_to(r.getInt("cr_steg_to"));
                q.setCr_niere_to(r.getShort("cr_niere_to"));
                q.setCa_to(r.getShort("ca_to"));
                q.setCt_to(r.getDouble("ct_to"));
                q.setCk_to(r.getDouble("ck_to"));

                queries.add(q);
            }
            return queries;
        }catch (SQLException e){
            return null;
        }
    }

    /**
     *  gets total number of rows in Query table (i.e. item count)
     *
     * @return Integer if successful otherwise -1
     * */
    public static int getTotalNumberOfQueriesWithFilters(Pair<Date, Date> dateFromTo, List<User> users){
        String sqlString = "SELECT COUNT(query_id) as query_total_count FROM multiexcel.queries ";
        sqlString += (new HistoryManager()).CreateQueryString(dateFromTo, users);

        System.out.println("Filtered Count Query: " + sqlString);
        try(
                PreparedStatement s = DBS.getConnection().prepareStatement(sqlString);
                ResultSet r = s.executeQuery()
        ){
            if(r.next()){
                return r.getInt("query_total_count");
            }
            return  0;
        }catch (SQLException e){
            return -1;
        }
    }
}
