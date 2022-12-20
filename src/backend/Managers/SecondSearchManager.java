package backend.Managers;

import backend.Entities.Category;
import backend.Entities.Part;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SecondSearchManager {

    public static List<Part> search(List<Part> parts, List<Category> categories, boolean ratingSort) {
        List<Part> output = new ArrayList<>();
        List<Integer> categoriesId = categories.stream().map(Category::getCategory_id).collect(Collectors.toList());
        List<Part> matchingCategories = parts.stream().filter(x -> categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());
        List<Part> notMatchingCategories = parts.stream().filter(x -> !categoriesId.contains(x.getCategory_id().intValue())).collect(Collectors.toList());
        if(ratingSort){
            matchingCategories.sort(Comparator.comparingInt(Part::getRating));
            notMatchingCategories.sort(Comparator.comparingInt(Part::getRating));
        }
        matchingCategories.addAll(notMatchingCategories);
        return output;
    }
}
