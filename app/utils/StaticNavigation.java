package utils;

import models.ProductCategory;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static utils.NavNode.leaf;
import static utils.NavNode.root;

/**
 * Created by andy on 5/5/15.
 */
public class StaticNavigation {
    public static String main = "Главная";

    public static SortedSet<NavNode> get() {
        return new TreeSet<>(Arrays.asList(
                leaf("Главная", "#", 1),
                root("Прайс-Лист", "#", 2,
                        leaf("Производство", "#", 1),
                        leaf("Нанесение", "#", 2)),
                leaf("Фотогалерея", "#", 3),
                leaf("О компании", "#", 4),
                leaf("Контакты", "#", 5)));
    }


    public static SortedSet<NavNode> convert(List<ProductCategory> roots) {
        SortedSet<NavNode> catalogNavigation = new TreeSet<>();
        for (ProductCategory root : roots) {
            NavNode item = putChild(root, null);
            catalogNavigation.add(item);
        }
        return catalogNavigation;
    }

    private static NavNode putChild(ProductCategory child, NavNode root) {
        NavNode item = new NavNode(child);
        if (root != null) root.nodes.add(item);
        for (ProductCategory c : child.getSubCategories()) {
            putChild(c, item);
        }
        return item;
    }

}
