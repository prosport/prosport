package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ProductCategory;
import models.TreeNode;
import org.apache.commons.lang3.StringEscapeUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.tree2;

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
        JsonNode node = Json.toJson(treeNodes);

        return ok(tree2.render("Дерево", node.toString()));
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
        result.children.addAll(convertToTree(category.getSubCategories()));
        return result;
    }


}
