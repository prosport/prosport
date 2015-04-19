package utils

import scala.collection.mutable


object Temporary {
  val topSiteMenuActive = "Главная"
  val topSiteMenu = mutable.LinkedHashMap(
    "Главная" -> "#",
    "Прайс-Лист" -> "#",
    "Каталог" -> "#",
    "Фотогалерея" -> "#",
    "Форум" -> "#",
    "О компании" -> "#",
    "Контакты" -> "#"
  )

}