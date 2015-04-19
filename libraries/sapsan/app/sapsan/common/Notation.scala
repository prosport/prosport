package sapsan.common


object Notation {

    /** Преобразование ВотТакойНотации в такую_нотацию */
    //val toCNotation = (for(ch <- name) yield if(ch.isUpper) "_" + ch.toLower else ch).mkString.drop(1)
    def camelToC(text: String) = {
        val result = text.toCharArray.map(ch => if(ch.isUpper) "_" + ch.toLower else ch ).mkString
        if(result.head == '_') result.drop(1)
        else result
    }

    /** Преобразование вот_такой_нотации в ТакуюНотацию */
    def cToCamel(text: String) = if(text != null) text.split('_').map(_.capitalize).mkString("") else ""

    /** Сделать заглавной первую букву - Преобразование верблюжьей в имя класса, константы ...  */
    def toClassName(text: String) = text.capitalize

    /** Сделать прописной нижнюю букву - преобразование верблюжьей в имя метода, переменной, ... */
    def toMemberName(text: String) = text.head.toLower + text.tail

    /** Из вот_такой_нотации в "Вот такую нотацию" */
    def toWords(text: String) = text.replace('_', ' ').capitalize


}