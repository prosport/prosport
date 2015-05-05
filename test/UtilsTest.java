import org.junit.Assert;
import org.junit.Test;
import utils.NavNode;

import static utils.NavNode.leaf;
import static utils.NavNode.root;

/**
 * Created by andy on 5/5/15.
 */
public class UtilsTest {

    @Test
    public void navNodeTest() {
        NavNode  n = root("title", "url", leaf("child1", "#"), leaf("child2", "#"));
        Assert.assertEquals(n.nodes.size(), 2);
    }
}
