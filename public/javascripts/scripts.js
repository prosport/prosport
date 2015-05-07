$(document).ready(function() {
    
    /*
     *   Слайдер
     */
    
    $('#general-clider').flexslider({
        namespace: 'flex_',
        animation: 'fade',
        selector: '.slides-list > li',
        animationSpeed: 0,
        direction: 'horisontal',
        controlNav: true,
        directionNav: true,
        keyboard: false,
        slideshow: false,
        slideshowSpeed: 5000,
        pauseOnHover: true,
        useCSS: true,
        touch: true,
        start: function(slider) {
            slider.vars.animationSpeed = 2000;
        }
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
            ((windowHeight / 2) < scrollTop) ? $('.button-ontop').addClass('visible') : $('.button-ontop').removeClass('visible');
        });
        
        $('.button-ontop').on('click', function() {
            var scrollTop = $(document).scrollTop(),
                windowHeight = $(window).height();
            if (windowHeight / 2 < scrollTop) $('body').animate({scrollTop:0}, 300);
        });
    })();
    
    /*
     *   Блок контактов в футере
     */
    
    (function() {
        $('.js-openFooterAddresses').on('click', function() {
            $('body').animate({scrollTop: $(document).outerHeight()}, 300);
            $('footer .footer-contacts').slideToggle(300);
        });
    })();
    
})