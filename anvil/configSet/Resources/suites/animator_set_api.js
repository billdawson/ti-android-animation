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

	this.name = "animator_set_api";
	this.tests = [
		{name: "factory"},
		{name: "settersAndGetters"},
		{name: "otherMethods"},
		{name: "accessPropertiesDirectly"}

	];

	this.factory = function(testRun) {
		valueOf(testRun, function(){animMod.createAnimatorSet();}).shouldNotThrowException();
		var obj = animMod.createAnimatorSet();
		valueOf(testRun, "" + obj).shouldBe("[object AnimatorSet]");

		finish(testRun);
	};

	this.settersAndGetters = function(testRun) {
		var animSet = animMod.createAnimatorSet(),
			values;

		// duration
		valueOf(testRun, function(){animSet.setDuration(10000);}).shouldNotThrowException();
		valueOf(testRun, animSet.getDuration()).shouldBe(10000);

		// interpolator
		valueOf(testRun, function(){animSet.setInterpolator(animMod.LINEAR_INTERPOLATOR);}).shouldNotThrowException();
		valueOf(testRun, animSet.getInterpolator()).shouldBe(animMod.LINEAR_INTERPOLATOR);

		// interpolatorValues
		valueOf(testRun, function(){animSet.setInterpolatorValues([1, 2]);}).shouldNotThrowException();
		valueOf(testRun, function(){animSet.getInterpolatorValues();}).shouldNotThrowException();
		values = animSet.getInterpolatorValues();
		valueOf(testRun, values.length).shouldBe(2);
		valueOf(testRun, values[0]).shouldBe(1);
		valueOf(testRun, values[1]).shouldBe(2);

		// startDelay
		valueOf(testRun, function() {animSet.setStartDelay(2000);}).shouldNotThrowException();
		valueOf(testRun, animSet.getStartDelay()).shouldBe(2000);

		// childAnimations (getter only)
		// see "otherMethods", because playTogether and playSequentially tests will test this.

		finish(testRun);
	};

	this.otherMethods = function(testRun) {
		var animSet = animMod.createAnimatorSet(),
			animations,
			returnAnimations,
			oneAnimation;

		// start
		valueOf(testRun, animSet.start).shouldBeFunction();

		// end
		valueOf(testRun, animSet.end).shouldBeFunction();

		// cancel
		valueOf(testRun, animSet.cancel).shouldBeFunction();

		// isRunning
		valueOf(testRun, animSet.isRunning).shouldBeFunction();

		// isStarted
		valueOf(testRun, animSet.isStarted).shouldBeFunction();

		// playTogether - test whether sets the child animations properly
		valueOf(testRun, animSet.playTogether).shouldBeFunction();
		animations = [animMod.objectAnimator.ofInt({}, "property1", 1),
			animMod.objectAnimator.ofInt({}, "property2", 1)];
		valueOf(testRun, function(){animSet.playTogether(animations);}).shouldNotThrowException();
		returnAnimations = animSet.childAnimations;
		valueOf(testRun, returnAnimations).shouldBeArray();
		valueOf(testRun, returnAnimations.length).shouldBe(animations.length);
		oneAnimation = returnAnimations[0];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property1");
		oneAnimation = returnAnimations[1];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property2");

		returnAnimations = animSet.getChildAnimations(); // testing getter
		valueOf(testRun, returnAnimations).shouldBeArray();
		valueOf(testRun, returnAnimations.length).shouldBe(2);
		oneAnimation = returnAnimations[0];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property1");
		oneAnimation = returnAnimations[1];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property2");
		animSet.playTogether(null);
		valueOf(animSet.childAnimations).shouldBeNull;

		// playSequentially - test whether sets the child animations properly
		valueOf(testRun, animSet.playSequentially).shouldBeFunction();
		animations = [animMod.objectAnimator.ofInt({}, "property1", 1),
			animMod.objectAnimator.ofInt({}, "property2", 1)];
		valueOf(testRun, function(){animSet.playSequentially(animations);}).shouldNotThrowException();
		returnAnimations = animSet.childAnimations;
		valueOf(testRun, returnAnimations).shouldBeArray();
		valueOf(testRun, returnAnimations.length).shouldBe(2);
		oneAnimation = returnAnimations[0];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property1");
		oneAnimation = returnAnimations[1];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property2");

		returnAnimations = animSet.getChildAnimations(); // testing getter
		valueOf(testRun, returnAnimations).shouldBeArray();
		valueOf(testRun, returnAnimations.length).shouldBe(2);
		oneAnimation = returnAnimations[0];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property1");
		oneAnimation = returnAnimations[1];
		valueOf(testRun, oneAnimation).shouldBeObject();
		valueOf(testRun, "" + oneAnimation).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, oneAnimation.propertyName).shouldBe("property2");
		animSet.playTogether(null);
		valueOf(testRun, animSet.childAnimations).shouldBeNull;

		finish(testRun);
	};

	this.accessPropertiesDirectly = function(testRun) {
		var animSet = animMod.createAnimatorSet(),
			values;

		// duration
		animSet.duration = 123;
		valueOf(testRun, animSet.duration).shouldBe(123);
		valueOf(testRun, animSet.getDuration()).shouldBe(123);
		animSet.setDuration(456);
		valueOf(testRun, animSet.duration).shouldBe(456);

		// interpolator
		animSet.interpolator = animMod.LINEAR_INTERPOLATOR;
		valueOf(testRun, animSet.interpolator).shouldBe(animMod.LINEAR_INTERPOLATOR);
		valueOf(testRun, animSet.getInterpolator()).shouldBe(animMod.LINEAR_INTERPOLATOR);
		animSet.setInterpolator(animMod.BOUNCE_INTERPOLATOR);
		valueOf(testRun, animSet.interpolator).shouldBe(animMod.BOUNCE_INTERPOLATOR);

		// interpolatorValues
		animSet.interpolatorValues = [1, 2];
		valueOf(testRun, animSet.interpolatorValues).shouldBeArray();
		valueOf(testRun, animSet.interpolatorValues.length).shouldBe(2);
		valueOf(testRun, animSet.interpolatorValues[0]).shouldBe(1);
		valueOf(testRun, animSet.interpolatorValues[1]).shouldBe(2);
		valueOf(testRun, animSet.getInterpolatorValues()).shouldBeArray();
		valueOf(testRun, animSet.getInterpolatorValues().length).shouldBe(2);
		valueOf(testRun, animSet.getInterpolatorValues()[0]).shouldBe(1);
		valueOf(testRun, animSet.getInterpolatorValues()[1]).shouldBe(2);
		animSet.setInterpolatorValues([3,4]);
		valueOf(testRun, animSet.interpolatorValues).shouldBeArray();
		valueOf(testRun, animSet.interpolatorValues.length).shouldBe(2);
		valueOf(testRun, animSet.interpolatorValues[0]).shouldBe(3);
		valueOf(testRun, animSet.interpolatorValues[1]).shouldBe(4);

		// startDelay
		animSet.startDelay = 2000;
		valueOf(testRun, animSet.startDelay).shouldBe(2000);
		valueOf(testRun, animSet.getStartDelay()).shouldBe(2000);
		animSet.setStartDelay(3000);
		valueOf(testRun, animSet.startDelay).shouldBe(3000);

		// childAnimations (read only)
		// see "otherMethods", because playTogether and playSequentially tests will test this.

		finish(testRun);

	};

};
