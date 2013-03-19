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

	this.name = "view_property_animator_api";
	this.tests = [
		{name: "factory"},
		{name: "methods"}

	];

	this.factory = function(testRun) {
		valueOf(testRun, "" + animMod.viewPropertyAnimator).shouldBe("[object ViewPropertyAnimatorFactory]");
		valueOf(testRun, "" + animMod.getViewPropertyAnimator()).shouldBe("[object ViewPropertyAnimatorFactory]");
		valueOf(testRun, animMod.viewPropertyAnimator.animate).shouldBeFunction();
		valueOf(testRun, function(){animMod.viewPropertyAnimator.animate(null);}).shouldThrowException();
		finish(testRun);
	};

	this.methods = function(testRun) {
		var win = Ti.UI.createWindow(),
			view = Ti.UI.createView();

		function runTest() {
			var a = animMod.viewPropertyAnimator.animate(view);

			// helper for shouldBeFunction
			function f(x) {valueOf(testRun, x).shouldBeFunction();}

			f(a.setDuration);
			f(a.getDuration);
			f(a.setStartDelay);
			f(a.getStartDelay);
			f(a.setInterpolator);
			f(a.setListener);
			f(a.withStartAction);
			f(a.withEndAction);
			f(a.start);
			f(a.cancel);
			f(a.x);
			f(a.xBy);
			f(a.y);
			f(a.yBy);
			f(a.alpha);
			f(a.alphaBy);
			f(a.opacity);
			f(a.opacityBy);
			f(a.rotation);
			f(a.rotationBy);
			f(a.rotationX);
			f(a.rotationXBy);
			f(a.rotationY);
			f(a.rotationYBy);
			f(a.scaleX);
			f(a.scaleXBy);
			f(a.scaleY);
			f(a.scaleYBy);
			f(a.translationX);
			f(a.translationXBy);
			f(a.translationY);
			f(a.translationYBy);

			finish(testRun);
		}

		win.add(view);
		win.addEventListener("open", runTest);
		win.open();

	};

};
