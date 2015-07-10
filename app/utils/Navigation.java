package utils;

import models.ProductCategory;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static utils.NavNode.leaf;
import static utils.NavNode.root;

/**
 * Created by andy on 5/5/15.
 */
public class Navigation {
    private static final Collector<NavNode, ?, Map<String, NavNode>> MAP_BY_TITLE_COLLECTOR =
            Collectors.toMap(n -> n.title, n -> n);
    public static final String MAIN = "Главная";
    public static final String MAIN_URL = "/";
    public static final String CATALOG = "Каталог";
    public static final String CATALOG_URL = "/catalog";
    public static final String PRICE = "Прайс-лист";
    public static final String PRICE_MANUFACTURE = "Производство";
    public static final String PRICE_MANUFACTURE_URL = "/price/manufacturing";
    public static final String PRICE_APPLICATION = "Нанесение";
    public static final String PRICE_APPLICATION_URL = "/price/application";


    //TODO: для read-only сделать unmodifiable map и шарить статику
    public static Map<String, NavNode> getStaticNavigationMap() {
        List<NavNode> staticNavigationList = Arrays.asList(
                leaf(MAIN, MAIN_URL, 1),
                leaf(CATALOG, CATALOG_URL, 2),
                root(PRICE, "/price", 3,
                        leaf(PRICE_MANUFACTURE, PRICE_MANUFACTURE_URL, 1),
                        leaf(PRICE_APPLICATION, PRICE_APPLICATION_URL, 2)),
                leaf("Фотогалерея", "#", 4),
                leaf("О компании", "#", 5),
                leaf("Контакты", "#", 6));

        return staticNavigationList.stream().collect(MAP_BY_TITLE_COLLECTOR);
    }


    public static List<NavNode> convertToNavNode(Collection<ProductCategory> roots) {
        List<NavNode> catalogNavigation = new ArrayList<>();
        for (ProductCategory root : roots) {
            NavNode item = putChildRecursive(root, null);
            catalogNavigation.add(item);
        }
        return catalogNavigation;
    }

    private static NavNode putChildRecursive(ProductCategory child, NavNode root) {
        NavNode item = new NavNode(child);
        if (root != null) root.children.add(item);
        for (ProductCategory c : child.getSubCategories()) {
            putChildRecursive(c, item);
        }
        return item;
    }

    public static boolean activateNodeWithTitle(@Nonnull String title, Collection<NavNode> nodes) {
        for (NavNode node : nodes) {
            if (title.equals(node.title)) {
                node.active = true;
                activateFirstChildrenCategories(node);
                return true;
            }
            if (node.hasChilds()) {
                node.active = activateNodeWithTitle(title, node.children);
                if (node.active) return true;
            }
        }
        return false;
    }

    private static void activateFirstChildrenCategories(NavNode node) {
        if (node.hasChilds()) {
            node.children.first().active = true;
            activateFirstChildrenCategories(node.children.first());
        }
    }
}
