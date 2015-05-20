package utils;

import models.ProductCategory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by andy on 5/14/15.
 */
public class StaticNavigationTest {

    @Test
    public void convertTest() {
        ProductCategory rootOne = new ProductCategory();
        rootOne.name = "Футболки";
        rootOne.url = "/t-shirts";
        rootOne.sortOrder = 1;

        ProductCategory rootTwo = new ProductCategory();
        rootTwo.name = "Костюмы";
        rootTwo.url = "/hoodies";
        rootTwo.sortOrder = 2;

        ProductCategory shirtOne = new ProductCategory();
        shirtOne.name = "Стандартные";
        shirtOne.url = "standartd";
        shirtOne.sortOrder = 1;

        ProductCategory shirtTwo = new ProductCategory();
        shirtTwo.name = "Длинный рукав";
        shirtTwo.url = "long-sleeve";
        shirtTwo.sortOrder = null;

        rootOne.subCategories.add(shirtTwo);
        rootOne.subCategories.add(shirtOne);

        SortedSet<NavNode> ss = StaticNavigation.convert(Arrays.asList(rootTwo, rootOne));
        List<NavNode> result = new ArrayList<>(ss.size());
        for(NavNode node : ss) {
            result.add(node);
        }
        Assert.assertEquals(1, result.get(0).sortOrder);
        Assert.assertEquals(rootOne.name, result.get(0).title);
        Assert.assertEquals(2, result.get(1).sortOrder);
        Assert.assertEquals(rootTwo.name, result.get(1).title);
        Assert.assertEquals(2, result.get(0).nodes.size());

        Assert.assertEquals(1, result.get(0).nodes.iterator().next().sortOrder);
    }
}
