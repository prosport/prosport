package utils;

import java.util.Arrays;
import java.util.List;

import static utils.NavNode.leaf;
import static utils.NavNode.root;

/**
 * Created by andy on 5/5/15.
 */
public class StaticNavigation {
    public static String main = "Главная";

    public static List<NavNode> get() {
        return Arrays.asList(
                leaf("Главная", "#"),
                root("Прайс-Лист", "#",
                        leaf("Производство", "#"),
                        leaf("Нанесение", "#")),
                root("Каталог", "/catalog",
                        root("Футболки", "#",
                                leaf("Дзюдо", "#"),
                                leaf("Самбо", "#"),
                                leaf("Борьба", "#")),
                        leaf("Длинный рукав", "#"),
                        leaf("Костюмы", "#"),
                        leaf("Толстовки", "#"),
                        leaf("Штаны", "#"),
                        leaf("Зимние шапки", "#")),
                leaf("Фотогалерея", "#"),
                leaf("О компании", "#"),
                leaf("Контакты", "#"));
    }
}
