package utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by andy on 4/17/15.
 */
public class TreeNode {
    public long id;
    public String text;
    public Set<TreeNode> children = new HashSet<>();
}
