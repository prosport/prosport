package models;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by andy on 4/17/15.
 */
public class TreeNode {
    public String text;
    public Set<TreeNode> nodes = new HashSet<>();
}
