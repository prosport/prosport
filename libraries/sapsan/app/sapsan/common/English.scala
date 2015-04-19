package sapsan.common

/**
 * Этот файл является частью программы "vorobei".
 * Любое распространение без письменного  разрешения автора запрещено!
 *
 * Автор: Румата Эсторский <rumata@sputnikchess.ru>
 * Создан: 27.07.13 в 16:31
 */
object English {


    /**
     * Перевод множественного числа существительного в единственное число
     * см. http://www.study.ru/support/handbook/noun3.html
     */
    def wordManyToOne(word: String) = word match {
        case w if w.length < 3 => word
        // по правилу countries => country
        case w if w.endsWith("ies") => word.dropRight(3) + 'y'
        // по правилу users => user
        case w if w.endsWith("s") => word.dropRight(1)
        case _ => word
    }

    /**
     * Перевод единственного числа имён существительных во множественное число
     */
//    def wordManyToOne(word: String) = word match {
//
//    }


}
