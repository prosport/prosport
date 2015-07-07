package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import utils.Navigation;

import models.TreeNode;
import models.ProductCategory;

import views.html.admin.master;

public class AdminController extends Controller {

    public static Result index() {
        return ok(master.render("Index", views.html.admin.index.render()));
    }

    public static Result categories() {
        List<ProductCategory> categories = ProductCategory.getRootCategories();
        List<TreeNode> treeNodes = convertToTree(categories);
        TreeNode root = new TreeNode();
        root.text = Navigation.CATALOG;
        root.children.addAll(treeNodes);
        JsonNode node = Json.toJson(root);
        return ok(master.render("Categories", views.html.admin.categories.render(node.toString())));
    }

    public static Result pages() {
        return ok(master.render("Pages", views.html.admin.pages.render()));
    }

    public static Result media() {
        return ok(master.render("Pages", views.html.admin.media.render()));
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