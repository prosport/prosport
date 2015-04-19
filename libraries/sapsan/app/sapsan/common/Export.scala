package sapsan.common

import java.nio.charset.Charset
//import com.avaje.ebean.Ebean


object Export {
    def toCSV(model: String, selectedColumns: Set[String], charset: Charset, separator: String) = {
        // TODO commented 17th feb 2015 because val m = Schema.models(model) one error found
        //        var result = ""
//        val m = Schema.models(model)
//
//        // Запрос к БД
//        import collection.JavaConversions._
//        val items = Ebean.find(m.clazz).findList //.toArray // TODO .toArray?
//
//        for(item <- items) {
//            for((name, field) <- m.fields if selectedColumns.contains(name)) {
//                import play.api.libs.json.Json
//                import play.api.libs.json.Json.toJson
//                //println(name + "= " + field.extract(item))
//                //result = result + field.extract(item) + separator
//                val jsonArray = Json.toJson(Seq(
//                    toJson(1), toJson("Bob"), toJson(3), toJson(4)
//                ))
//                result = result + jsonArray
//            }
//            result = result + "\n"
//        }
//
//        result
        ""
    }
}