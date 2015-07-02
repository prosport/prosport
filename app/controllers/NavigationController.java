package controllers;

import models.ProductCategory;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NavNode;
import utils.Navigation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andy on 26.06.15.
 */
public class NavigationController extends Controller {

    public static Collection<NavNode> getBreadCrumbsForCategory(String categoryTitle) {
        Collection<NavNode> nodes = getNavigation();
        Deque<NavNode> result = new ArrayDeque<>();
        searchNodeWithTitle(nodes, categoryTitle, result);
        return result;
    }

    private static boolean searchNodeWithTitle(Collection<NavNode> nodes, String title, Deque<NavNode> path) {
        for(NavNode node : nodes) {
            if(node.title.equals(title)) {
                path.push(node);
                return true;
            }
            if(node.hasChilds()) {
                boolean found = searchNodeWithTitle(node.nodes, title, path);
                if(found) {
                    path.push(node);
                    return true;
                }
            }
        }
        return false;
    }

    public static Collection<NavNode> getNavigationWithActiveCategory(String categoryTitle) {
        Collection<NavNode> result = getNavigation();
        Navigation.activateNodeWithTitle(categoryTitle, result);
        return result;
    }

    private static Collection<NavNode> getNavigation() {
        Map<String, NavNode> staticNavigation = Navigation.getStaticNavigationMap();

        List<ProductCategory> productCatalogRoots = ProductCategory.getRootCategories();
        List<NavNode> catalogNavigation = Navigation.convertToNavNode(productCatalogRoots);

        staticNavigation.get("Каталог").nodes.addAll(catalogNavigation);

        return staticNavigation.values()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }


    public static Result listCategories() {
        return ok(Arrays.toString(getNavigation().toArray()));
    }

}
