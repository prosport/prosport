package utils;

import models.ProductCategory;

import java.util.Arrays;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Created by andy on 5/5/15.
 */
public class NavNode implements Comparable<NavNode> {
    public static final int MAX_ORDER = 1000;

    public final int sortOrder;

    public final String title;
    public final String url;
    public final SortedSet<NavNode> children = new TreeSet<>();
    public boolean active;

    public boolean hasChilds() {
        return !children.isEmpty();
    }

    private NavNode(String title, String url, int sortOrder) {
        if(title == null) throw new IllegalArgumentException("title == null");
        if(url == null) throw new IllegalArgumentException("url == null");
        this.sortOrder = sortOrder;
        this.title = title;
        this.url = url;
    }

    public NavNode(ProductCategory category) {
        if(category.name == null) throw new IllegalArgumentException("category.name == null, category.id = " + category.id);
        if(category.url == null) throw new IllegalArgumentException("category.url == null, category.id = " + category.id);

        this.sortOrder = category.sortOrder == null ? MAX_ORDER : category.sortOrder;
        this.title = category.name;
        this.url =  category.getFullUrl();


    }

    public static NavNode leaf(String title, String url, int sortOrder) {
        return new NavNode(title, url, sortOrder);
    }

    public static NavNode root(String title, String url, int sortOrder, NavNode... childs) {
        NavNode result = new NavNode(title, url, sortOrder);
        result.children.addAll(Arrays.asList(childs));
        return result;
    }

    @Override
    public String toString() {
        return title + "(" + url + ")";
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        return Objects.equals(this.title, ((NavNode) obj).title);
    }

    @Override
    public int compareTo(NavNode o) {
        if(o == null) return 1;

        int compare = Integer.compare(this.sortOrder, o.sortOrder);
        return compare == 0 ? this.title.compareTo(o.title) : compare;
    }
}
