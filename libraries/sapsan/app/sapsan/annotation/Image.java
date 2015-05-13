package sapsan.annotation;

public @interface Image {

    String path() default "";

    boolean deleteOldFile() default true;

    int quality() default 100;

    String prefix() default "";
    //                    'resize' => array(500, 500, Image::AUTO),  // width, height, master dimension
//    'crop'   => array(100, 100, NULL, NULL),   // width, height, offset_x, offset_y
    // и в виде массива это!

}
