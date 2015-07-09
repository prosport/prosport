var gulp 					= require('gulp'),
	concatCSS 				= require('gulp-concat-css'),
	minifyCSS 				= require('gulp-minify-css'),
	less 	  				= require('gulp-less'),
	uglify 					= require('gulp-uglify'),
	gulpAutoprefixer 		= require('gulp-autoprefixer');
	rename 					= require('gulp-rename');
	concat 					= require('gulp-concat');

var config = {

	inputCSS: '../../public/stylesheets/styles.less',
	outputCSS: '../../public/stylesheets/',

	inputJS: [
		'../../public/javascripts/jquery-1.11.2.min.js',
		'../../public/javascripts/jquery.flexslider-min.js',
		'../../public/javascripts/scripts.js'
	],
	outputJS: '../../public/javascripts/'

};
 
gulp.task('maincss', function () {
	gulp.src(config.inputCSS)
		.pipe(less())
		.pipe(gulpAutoprefixer())
		.pipe(minifyCSS({keepBreaks:false}))
		.pipe(rename('styles.compiled.css'))
		.pipe(gulp.dest(config.outputCSS));
});

gulp.task('admincss', function() {
	gulp.src('../../public/stylesheets/admin.less')
		.pipe(less())
		.pipe(gulpAutoprefixer())
		.pipe(minifyCSS({keepBreaks: false}))
		.pipe(rename('admin.compiled.css'))
		.pipe(gulp.dest('../../public/stylesheets/'));
});

gulp.task('css', ['maincss', 'admincss']);

gulp.task('js', function () {
	gulp.src(config.inputJS)
		.pipe(concat('scripts.concat.js'))
		.pipe(uglify())
		.pipe(rename('scripts.compiled.js'))
		.pipe(gulp.dest(config.outputJS));
});