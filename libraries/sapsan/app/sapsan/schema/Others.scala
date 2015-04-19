package sapsan.schema

object ORM extends Enumeration {
    type ORM = Value
    val

    //
    // Java ORM
    //
    /** http://www.hibernate.org/ */
    Hibernate,
    /** http://www.avaje.org/ */
    Ebean,
    /** https://github.com/rufiao/persist */
    Persist,


    //
    // Scala ORM
    //

    /** Circumflex ORM http://circumflex.ru/ */
    Circumflex,

    /** http://squeryl.org/ */
    Squeryl,

    /**  http://sorm-framework.org/ */
    SORM,

    /** http://www.playframework.com/documentation/2.1.1/ScalaAnorm */
    Anorm,

    /** http://slick.typesafe.com/ */
    Slick

    = Value
}
