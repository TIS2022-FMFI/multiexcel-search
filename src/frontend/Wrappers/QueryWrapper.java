package frontend.Wrappers;

import backend.Entities.Category;
import backend.Entities.Category_query;
import backend.Entities.Query;
import backend.Entities.User;
import backend.Managers.CategoryManager;
import backend.Managers.UserManager;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryWrapper {
    private final Query query;

    //refs to HistoryMainController maps
    private final Map<Integer, String> userIdToName;
    private final Map<BigInteger, String> categoryIdToName;

    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public QueryWrapper(Query query, Map<Integer, String> userIdToName, Map<BigInteger, String> categoryIdToName) {
        this.query = query;
        this.userIdToName = userIdToName;
        this.categoryIdToName = categoryIdToName;
    }

    public Query getQuery() {
        return query;
    }

    public String getCategories() {
        List<Category_query> category_queries = CategoryManager.getAllCategoriesByQueryId(query.getQuery_id());

        if (category_queries == null) {
            return "<none>";
        }

        if (category_queries.size() == 0) {
            return "<none>";
        }

        List<String> displayedCategories = new ArrayList<>();

        for (Category_query cq : category_queries) {
            if (!categoryIdToName.containsKey(cq.getCategory_id())) {
                Category category = CategoryManager.getCategory(cq.getCategory_id());
                if (category == null) {
                    continue;
                }
                categoryIdToName.put(cq.getCategory_id(), category.getCategory_name());
            }
            displayedCategories.add(categoryIdToName.get(cq.getCategory_id()));
        }
        return String.join(", ", displayedCategories);
    }

    public String getUsername() {
        return fetchUsernameString(query.getUser_id());
    }

    public String getRubber() {
        return createInterval(query.getRubber_from(), query.getRubber_to());
    }

    public String getDiameter_AT() {
        return createInterval(query.getDiameter_AT_from(), query.getDiameter_AT_to());
    }


    public String getLength_L_AT() {
        return createInterval(query.getLength_L_AT_from(), query.getLength_L_AT_to());
    }

    public String getDiameter_IT() {
        return createInterval(query.getDiameter_IT_from(), query.getDiameter_IT_to());
    }

    public String getLength_L_IT() {
        return createInterval(query.getLength_L_IT_from(), query.getLength_L_IT_to());
    }

    public String getDiameter_ZT() {
        return createInterval(query.getDiameter_ZT_from(), query.getDiameter_ZT_to());
    }

    public String getLength_L_ZT() {
        return createInterval(query.getLength_L_ZT_from(), query.getLength_L_ZT_to());
    }

    public String getCr_steg() {
        return createInterval(query.getCr_steg_from(), query.getCr_steg_to());
    }

    public String getCr_niere() {
        return createInterval(query.getCr_niere_from(), query.getCr_niere_to());
    }

    public String getCa() {
        return createInterval(query.getCa_from(), query.getCa_to());
    }

    public String getCk() {
        return createInterval(query.getCk_from(), query.getCk_to());
    }

    public String getCt() {
        return createInterval(query.getCt_from(), query.getCt_to());
    }

    public String getDate() {
        return query.getDate().toLocalDateTime().format(dateTimeFormat);
    }

    public void setDateTimeFormat(DateTimeFormatter dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    private <K, T> String createInterval(K from, T to) {
        if (from == null && to == null) {
            return "-";
        } else if (from == null) {
            return String.format("≤ %s", to);
        } else if (to == null) {
            return String.format("%s ≥", from);
        } else {
            if (from.equals(to)) {
                return from.toString();
            }
            return String.format("%s - %s", from, to);
        }
    }

    private String fetchUsernameString(int userId) {
        if (!userIdToName.containsKey(userId)) {
            User user = UserManager.getUserById(userId);
            if (user == null) {
                return "user doesnt exist";
            }
            userIdToName.put(userId, user.getUser_name());
        }

        return userIdToName.get(userId);
    }
}
