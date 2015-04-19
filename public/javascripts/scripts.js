$(document).ready(function() {
    
    /*
     *   Слайдер
     */
    
    $('#general-clider').flexslider({
        namespace: 'flex_',
        animation: 'fade',
        selector: '.slides-list > li',
        controlsContainer: '#general-clider .sliderButtons_box',
        animationSpeed: 2000,
        direction: 'horisontal',
        controlNav: true,
        directionNav: true,
        keyboard: false,
        slideshow: false,
        slideshowSpeed: 5000,
        pauseOnHover: true,
        useCSS: true,
        touch: true
    });
    
    /*
     *   Меню
     */
    
    (function() {
        $(document).on('scroll', function(e) {
            var scrollTop = $(document).scrollTop();
            if ($('header').outerHeight() <= scrollTop) {
                $('.wrapper')
                    .addClass('fixed-menu')
                    .css('padding-top', $('nav').outerHeight());
            } else {
                $('.wrapper')
                    .removeClass('fixed-menu')
                    .css('padding-top', '');
            }
        });
        $(document).trigger('scroll');
    })();
    
    /*
     *   Кнопка "На верх"
     */
    
    (function() {
        $(document).on('scroll', function(e) {
            var scrollTop = $(document).scrollTop(),
                windowHeight = $(window).height();
            if (windowHeight / 2 < scrollTop) {
                $('.button-ontop').addClass('visible');
            } else {
                $('.button-ontop').removeClass('visible');
            }
        });
        
        $('.button-ontop').on('click', function() {
            var scrollTop = $(document).scrollTop(),
                windowHeight = $(window).height();
            if (windowHeight / 2 < scrollTop) {
                $('body').animate({scrollTop:0}, 300);
            }
        });
    })();
    
})