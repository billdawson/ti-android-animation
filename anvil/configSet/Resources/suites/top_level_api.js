module.exports = new function() {
	var finish,
		valueOf, 
		animMod = require("com.billdawson.timodules.animation");

	this.init = function(testUtils) {
		finish = testUtils.finish;
		valueOf = testUtils.valueOf;
	};

	this.options = {
		forceBuild: true
	};

	this.name = "top_level_api";
	this.tests = [
		{name: "constants"},
		{name: "properties"},
		{name: "methods"},
		{name: "toPixels"}

	];

	this.constants = function(testRun) {
		valueOf(testRun, animMod.INT_EVALUATOR).shouldBeNumber();
		valueOf(testRun, animMod.FLOAT_EVALUATOR).shouldBeNumber();
		valueOf(testRun, animMod.ARGB_EVALUATOR).shouldBeNumber();
		valueOf(testRun, animMod.INFINITE).shouldBeNumber();
		valueOf(testRun, animMod.RESTART).shouldBeNumber();
		valueOf(testRun, animMod.REVERSE).shouldBeNumber();
		valueOf(testRun, animMod.ACCELERATE_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.DECELERATE_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.ACCELERATE_DECELERATE_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.ANTICIPATE_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.ANTICIPATE_OVERSHOOT_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.BOUNCE_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.CYCLE_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.OVERSHOOT_INTERPOLATOR).shouldBeNumber();
		valueOf(testRun, animMod.LINEAR_INTERPOLATOR).shouldBeNumber();


		finish(testRun);
	};

	this.properties = function(testRun) {
		valueOf(testRun, "" + animMod.viewPropertyAnimator).shouldBe("[object ViewPropertyAnimatorFactory]");
		valueOf(testRun, "" + animMod.objectAnimator).shouldBe("[object ObjectAnimatorFactory]");
		finish(testRun);
	};

	this.methods = function(testRun) {
		var win,
			view;
		valueOf(testRun, "" + animMod.getViewPropertyAnimator()).shouldBe("[object ViewPropertyAnimatorFactory]");
		valueOf(testRun, "" + animMod.getObjectAnimator()).shouldBe("[object ObjectAnimatorFactory]");

		finish(testRun);

	};

	this.toPixels = function(testRun) {
		var win,
			view;

		valueOf(testRun, animMod.toPixels).shouldBeFunction();

		function test() {
			var pixelValue = Math.floor(animMod.toPixels(view, "16dp")),
				manualResult = Math.floor(16 * Ti.Platform.displayCaps.logicalDensityFactor);
			valueOf(testRun, manualResult).shouldBe(pixelValue);

			finish(testRun);
		}

		win = Ti.UI.createWindow();
		win.add(view = Ti.UI.createView());
		win.addEventListener("open", test);
		win.open();
	};

};
