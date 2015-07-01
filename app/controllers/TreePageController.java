package controllers;

import models.ProductCategory;
import models.TreeNode;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by andy on 4/17/15.
 */
public class TreePageController extends Controller {

    public static Result GET() {
        List<ProductCategory> categories = ProductCategory.getRootCategories();
        return ok(tree.render("Дерево", convertToTree(categories)));
    }


    public static List<TreeNode> convertToTree(Collection<ProductCategory> categories) {
        List<TreeNode> result = new ArrayList<>(categories.size());
        for (ProductCategory category : categories) {
            result.add(convertNode(category));
        }
        return result;
    }

    public static TreeNode convertNode(ProductCategory category) {
        TreeNode result = new TreeNode();
        result.text = category.name;
        result.nodes.addAll(convertToTree(category.getSubCategories()));
        return result;
    }


}
