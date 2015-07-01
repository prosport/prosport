package controllers;

import models.ProductCategory;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NavNode;
import utils.StaticNavigation;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by andy on 26.06.15.
 */
public class NavigationController extends Controller {

    public static SortedSet<NavNode> getNavigation() {

        List<ProductCategory> roots = ProductCategory.findAllRoots();
        SortedSet<NavNode> catalogNavigation = StaticNavigation.convert(roots);

        NavNode catalog = NavNode.root("Каталог", "#", 2);
        catalog.nodes.addAll(catalogNavigation);
        SortedSet<NavNode> staticNavigation = StaticNavigation.get();
        staticNavigation.add(catalog);
        return staticNavigation;
    }

    public static Result listCategories() {
        return ok(Arrays.toString(getNavigation().toArray()));
    }

}
