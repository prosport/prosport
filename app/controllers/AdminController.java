package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ProductCategory;
import play.api.templates.Html;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Navigation;
import utils.TreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import views.html.admin.master;

public class AdminController extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
        //TODO: changed from views.html.admin.index.render()
        return ok(master.render("Index", views.html.index.render(), session().get("email")));
    }

    public static Result categories() {
        List<ProductCategory> categories = ProductCategory.getRootCategories();
        List<TreeNode> treeNodes = convertToTree(categories);
        TreeNode root = new TreeNode();
        root.text = Navigation.CATALOG;
        root.children.addAll(treeNodes);
        JsonNode node = Json.toJson(root);

        Html categoriesHtml = views.html.admin.categories.render(node.toString());
        String email = session().get("email");
        return ok(master.render("Categories", categoriesHtml, email));
    }

    public static List<TreeNode> convertToTree(Collection<ProductCategory> categories) {
        List<TreeNode> result = new ArrayList<>(categories.size());
        result.addAll(categories
                .stream()
                .map(AdminController::convertNode)
                .collect(Collectors.toList()));
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