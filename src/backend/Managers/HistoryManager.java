package backend.Managers;

import backend.Entities.Query;
import backend.Entities.User;
import backend.DBS;
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
        if (queryUsers){
            queryString.append("WHERE ");
            for (User u : users) {
                if (isFirst){
                    queryString.append(columnUserId).append(u.getUser_id());
                    isFirst = false;
                }else{
                    queryString.append(" OR ").append(columnUserId).append(u.getUser_id());
                }
            }
            queryString.append(" ");
        }

        if(queryDate){
            if (queryString.length() == 0){
                queryString.append("WHERE ");
            }else{
                queryString.append("AND ");
            }
            queryString.append("date >= ").append(dateFromTo.getKey());
            queryString.append(" AND date <= ").append(dateFromTo.getValue());
            queryString.append(" ");
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
    public static List<Query> getQueriesWithFilters(Pair<Date, Date> dateFromTo, List<User> users, int queriesPerPage, int pageIndex){

        int itemsPerPage;
        int pageNumber;
        itemsPerPage = Math.max(queriesPerPage, 0);
        pageNumber = Math.max(pageIndex-1, 0);
        
        
        String sqlString = "SELECT * FROM multiexcel.queries ";
        sqlString += (new HistoryManager()).CreateQueryString(dateFromTo, users);

        sqlString += String.format("ORDER BY date ASC LIMIT %d, %d",pageNumber * itemsPerPage, itemsPerPage);
        //sqlString+= "ORDER BY date ASC LIMIT " + pageNumber * itemsPerPage + ", " + itemsPerPage;
        System.out.println("Query: " + sqlString);
        try(
                PreparedStatement s = DBS.getConnection().prepareStatement(sqlString);
                ResultSet r = s.executeQuery()
        ){
            List<Query> queries = new ArrayList<>();
            while (r.next()){
                Query q = new Query();
                q.setQuery_id(r.getInt("query_id"));
                q.setUser_id(r.getInt("user_id"));
                q.setRubber(r.getShort("rubber"));
                q.setDiameter_AT(r.getDouble("diameter_AT"));
                q.setLength_L_AT(r.getDouble("length_L_AT"));
                q.setDiameter_IT(r.getDouble("diameter_IT"));
                q.setLength_L_IT(r.getDouble("length_L_IT"));
                q.setDiameter_ZT(r.getDouble("diameter_ZT"));
                q.setLength_L_ZT(r.getDouble("length_L_ZT"));
                q.setCr_steg(r.getInt("cr_steg"));
                q.setCr_niere(r.getShort("cr_niere"));
                q.setCa(r.getShort("ca"));
                q.setCt(r.getDouble("ct"));
                q.setCk(r.getDouble("ck"));
                q.setDate(r.getDate("date"));

                queries.add(q);
            }
            return queries;
        }catch (SQLException e){
            return null;
        }
    }
}
