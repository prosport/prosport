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

    public static Collection<NavNode> getNavigation(String activeNodeTitle) {
        Collection<NavNode> result = getNavigation();
        Navigation.activateNodeWithTitle(activeNodeTitle, result);
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
