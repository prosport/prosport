package sapsan.common


object Notation {

    /** Converts ThisNonation into this_notation */
    //val toCNotation = (for(ch <- name) yield if(ch.isUpper) "_" + ch.toLower else ch).mkString.drop(1)
    def camelToC(text: String) = {
        val result = text.toCharArray.map(ch => if(ch.isUpper) "_" + ch.toLower else ch ).mkString
        if(result.head == '_') result.drop(1)
        else result
    }

    /** Converts this_notation into ThisNonation */
    def cToCamel(text: String) = if(text != null) text.split('_').map(_.capitalize).mkString("") else ""

    def toClassName(text: String) = text.capitalize

    def toMemberName(text: String) = text.head.toLower + text.tail

    /** Converts this_nonation into "this notation" */
    def toWords(text: String) = text.replace('_', ' ').capitalize


}