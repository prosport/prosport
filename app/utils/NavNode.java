package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andy on 5/5/15.
 */
public class NavNode {
    public final String title;
    public final String url;
    public final List<NavNode> nodes;

    public boolean hasChilds() {
        return !nodes.isEmpty();
    }

    private NavNode(String title, String url) {
        this.title = title;
        this.url = url;
        this.nodes = new ArrayList<>();
    }

    public static NavNode leaf(String title, String url) {
        return new NavNode(title, url);
    }

    public static NavNode root(String title, String url, NavNode ... childs) {
        NavNode result = new NavNode(title, url);
        result.nodes.addAll(Arrays.asList(childs));
        return result;
    }
}
