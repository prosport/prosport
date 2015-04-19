package sapsan.annotation;

/**
 * Этот файл является частью программы "snegir-education".
 * Любое распространение без письменного  разрешения автора запрещено!
 * <p/>
 * <p/>
 * Призван указывать на поля, которые хранят картинки, но хранят их в ФАЙЛОВОЙ
 * системе, а в базе хранят только ссылки на них. То есть он может аннотировать
 * только строковые поля.
 * <p/>
 * Частично совместим с Jelly_Field_Image
 * <p/>
 * <p/>
 * <p/>
 * Автор: Румата Эсторский <rumata@sputnikchess.ru>
 * Создан: 21.03.13 в 23:12
 */
public @interface Image {

    String path() default "";

    boolean deleteOldFile() default true;

    int quality() default 100;

    String prefix() default "";
    //                    'resize' => array(500, 500, Image::AUTO),  // width, height, master dimension
//    'crop'   => array(100, 100, NULL, NULL),   // width, height, offset_x, offset_y
    // и в виде массива это!

}
