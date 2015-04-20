package utils

import scala.collection.mutable


object Temporary {
  val topSiteMenuActive = "Главная"
  val topSiteMenu = mutable.LinkedHashMap(
    "Главная" -> "#",
    "Прайс-Лист" -> "#",
    "Каталог" -> "/catalog",
    "Фотогалерея" -> "#",
    "Форум" -> "#",
    "О компании" -> "#",
    "Контакты" -> "#"
  )

}