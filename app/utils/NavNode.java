package utils;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andy on 5/5/15.
 */
public class NavNode {
    public final int sortOrder;
    public final String title;
    public final String url;
    public final List<NavNode> nodes;

    public boolean hasChilds() {
        return !nodes.isEmpty();
    }

    //TODO: builder that increment sort order
    private NavNode(String title, String url) {
        this.sortOrder = 0;
        this.title = title;
        this.url = url;
        this.nodes = new ArrayList<>();
    }

    public static NavNode leaf(String title, String url) {
        return new NavNode(title, url);
    }

    public static NavNode root(String title, String url, NavNode... childs) {
        NavNode result = new NavNode(title, url);
        result.nodes.addAll(Arrays.asList(childs));
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

        NavNode rhs = (NavNode) obj;

        return new EqualsBuilder()
                .append(title, rhs.title)
                .isEquals();
    }
}
