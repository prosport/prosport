package sapsan.common

object English {

    // see http://www.study.ru/support/handbook/noun3.html
    def getPluralFormOfNoun(word: String) = word match {
        case w if w.length < 3 => word
        // from rule: countries => country
        case w if w.endsWith("ies") => word.dropRight(3) + 'y'
        // from rule: users => user
        case w if w.endsWith("s") => word.dropRight(1)
        case _ => word
    }

}
