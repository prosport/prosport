package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ProductCategory;
import utils.TreeNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Navigation;
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

        List<TreeNode> treeNodes = convertToTree(categories);
        TreeNode root = new TreeNode();
        root.text = Navigation.CATALOG;
        root.children.addAll(treeNodes);
        JsonNode node = Json.toJson(root);


        return ok(tree.render("Дерево", node.toString()));
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
        result.id = category.id;
        result.text = category.name;
        result.children.addAll(convertToTree(category.getSubCategories()));
        return result;
    }


}
