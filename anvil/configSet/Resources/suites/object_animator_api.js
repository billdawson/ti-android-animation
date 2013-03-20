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

	this.name = "object_animator_api";
	this.tests = [
		{name: "factoryMethods"},
		{name: "settingGoodAnimationValues"},
		{name: "settingBadAnimationValues"},
		{name: "settersAndGetters"},
		{name: "otherMethods"},
		{name: "accessPropertiesDirectly"}

	];

	this.factoryMethods = function(testRun) {
		valueOf(testRun, "" + (animMod.objectAnimator.ofInt({},"fakeProperty", 1))).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, "" + (animMod.objectAnimator.ofFloat({},"fakeProperty", 1))).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, "" + (animMod.createObjectAnimator())).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, "" + (animMod.objectAnimator.ofInt({},"fakeProperty", 1, 1))).shouldBe("[object ObjectAnimator]");
		valueOf(testRun, "" + (animMod.objectAnimator.ofFloat({},"fakeProperty", 1, 1))).shouldBe("[object ObjectAnimator]");
		finish(testRun);
	};

	this.settingGoodAnimationValues = function(testRun) {
		var objAnim = animMod.createObjectAnimator();
		valueOf(testRun, function(){objAnim.setIntValues(1);}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setIntValues(1,1);}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setFloatValues(1);}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setFloatValues(1,1);}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setIntValues("red");}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setIntValues("red","green");}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setIntValues("#fff");}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.setIntValues("#fff","#ff0");}).shouldNotThrowException();
		finish(testRun);
	};

	this.settingBadAnimationValues = function(testRun) {
		var objAnim = animMod.createObjectAnimator();
		// intValues will accept strings because we attempt to turn them into color codes.
		valueOf(testRun, function(){objAnim.setFloatValues("a");}).shouldThrowException();
		valueOf(testRun, function(){objAnim.setFloatValues(1, "a");}).shouldThrowException();
		valueOf(testRun, function(){objAnim.setFloatValues({});}).shouldThrowException();
		valueOf(testRun, function(){objAnim.setFloatValues([1,2,3]);}).shouldThrowException();
		valueOf(testRun, function(){objAnim.setIntValues({});}).shouldThrowException();
		valueOf(testRun, function(){objAnim.setIntValues([1,2,3]);}).shouldThrowException();
		finish(testRun);
	};

	this.settersAndGetters = function(testRun) {
		var objAnim = animMod.createObjectAnimator(),
			values,
			returnObj,
			targetObj = {testVal: "howdy"};

		// evaluator
		valueOf(testRun, function(){objAnim.setEvaluator(animMod.ARGB_EVALUATOR);}).shouldNotThrowException();
		valueOf(testRun, objAnim.getEvaluator()).shouldBe(animMod.ARGB_EVALUATOR);

		// propertyName
		valueOf(testRun, function(){objAnim.setPropertyName("mickey");}).shouldNotThrowException();
		valueOf(testRun, objAnim.getPropertyName()).shouldBe("mickey");
	
		// repeatCount
		valueOf(testRun, function(){objAnim.setRepeatCount(1);}).shouldNotThrowException();
		valueOf(testRun, objAnim.getRepeatCount()).shouldBe(1);

		// repeatMode
		valueOf(testRun, function(){objAnim.setRepeatMode(animMod.INFINITE);}).shouldNotThrowException();
		valueOf(testRun, objAnim.getRepeatMode()).shouldBe(animMod.INFINITE);

		// duration
		valueOf(testRun, function(){objAnim.setDuration(10000);}).shouldNotThrowException();
		valueOf(testRun, objAnim.getDuration()).shouldBe(10000);

		// interpolator
		valueOf(testRun, function(){objAnim.setInterpolator(animMod.LINEAR_INTERPOLATOR);}).shouldNotThrowException();
		valueOf(testRun, objAnim.getInterpolator()).shouldBe(animMod.LINEAR_INTERPOLATOR);

		// interpolatorValues
		valueOf(testRun, function(){objAnim.setInterpolatorValues(1, 2);}).shouldNotThrowException();
		valueOf(testRun, function(){objAnim.getInterpolatorValues();}).shouldNotThrowException();
		values = objAnim.getInterpolatorValues();
		valueOf(testRun, values.length).shouldBe(2);
		valueOf(testRun, values[0]).shouldBe(1);
		valueOf(testRun, values[1]).shouldBe(2);

		// target
		valueOf(testRun, function() {objAnim.setTarget(targetObj);}).shouldNotThrowException();
		valueOf(testRun, function() {objAnim.getTarget();}).shouldNotThrowException();
		returnObj = objAnim.getTarget();
		valueOf(testRun, returnObj.testVal).shouldBe(targetObj.testVal);

		// startDelay
		valueOf(testRun, function() {objAnim.setStartDelay(2000);}).shouldNotThrowException();
		valueOf(testRun, objAnim.getStartDelay()).shouldBe(2000);

		finish(testRun);
	};

	this.otherMethods = function(testRun) {
		var objAnim = animMod.createObjectAnimator();

		// start
		valueOf(testRun, objAnim.start).shouldBeFunction();

		// end
		valueOf(testRun, objAnim.end).shouldBeFunction();

		// cancel
		valueOf(testRun, objAnim.cancel).shouldBeFunction();

		// isRunning
		valueOf(testRun, objAnim.isRunning).shouldBeFunction();

		// isStarted
		valueOf(testRun, objAnim.isStarted).shouldBeFunction();

		finish(testRun);
	};

	this.accessPropertiesDirectly = function(testRun) {
		var objAnim = animMod.createObjectAnimator(),
			values,
			returnObj,
			targetObj = {testVal: "howdy"},
			anotherTargetObj = {testVal: "doody"}

		// evaluator
		objAnim.evaluator = animMod.ARGB_EVALUATOR;
		valueOf(testRun, objAnim.evaluator).shouldBe(animMod.ARGB_EVALUATOR);
		valueOf(testRun, objAnim.getEvaluator()).shouldBe(animMod.ARGB_EVALUATOR);
		objAnim.setEvaluator(animMod.INT_EVALUATOR);
		valueOf(testRun, objAnim.evaluator).shouldBe(animMod.INT_EVALUATOR);

		// propertyName
		objAnim.propertyName = "test1";
		valueOf(testRun, objAnim.propertyName).shouldBe("test1");
		valueOf(testRun, objAnim.getPropertyName()).shouldBe("test1");
		objAnim.setPropertyName("test2");
		valueOf(testRun, objAnim.propertyName).shouldBe("test2");
	
		// repeatCount
		objAnim.repeatCount = 123;
		valueOf(testRun, objAnim.repeatCount).shouldBe(123);
		valueOf(testRun, objAnim.getRepeatCount()).shouldBe(123);
		objAnim.setRepeatCount(456);
		valueOf(testRun, objAnim.repeatCount).shouldBe(456);

		// repeatMode
		objAnim.repeatMode = animMod.INFINITE;
		valueOf(testRun, objAnim.repeatMode).shouldBe(animMod.INFINITE);
		valueOf(testRun, objAnim.getRepeatMode()).shouldBe(animMod.INFINITE);
		objAnim.setRepeatMode(animMod.REVERSE);
		valueOf(testRun, objAnim.repeatMode).shouldBe(animMod.REVERSE);

		// duration
		objAnim.duration = 123;
		valueOf(testRun, objAnim.duration).shouldBe(123);
		valueOf(testRun, objAnim.getDuration()).shouldBe(123);
		objAnim.setDuration(456);
		valueOf(testRun, objAnim.duration).shouldBe(456);

		// interpolator
		objAnim.interpolator = animMod.LINEAR_INTERPOLATOR;
		valueOf(testRun, objAnim.interpolator).shouldBe(animMod.LINEAR_INTERPOLATOR);
		valueOf(testRun, objAnim.getInterpolator()).shouldBe(animMod.LINEAR_INTERPOLATOR);
		objAnim.setInterpolator(animMod.BOUNCE_INTERPOLATOR);
		valueOf(testRun, objAnim.interpolator).shouldBe(animMod.BOUNCE_INTERPOLATOR);

		// interpolatorValues
		objAnim.interpolatorValues = [1, 2];
		valueOf(testRun, objAnim.interpolatorValues).shouldBeArray();
		valueOf(testRun, objAnim.interpolatorValues.length).shouldBe(2);
		valueOf(testRun, objAnim.interpolatorValues[0]).shouldBe(1);
		valueOf(testRun, objAnim.interpolatorValues[1]).shouldBe(2);
		valueOf(testRun, objAnim.getInterpolatorValues()).shouldBeArray();
		valueOf(testRun, objAnim.getInterpolatorValues().length).shouldBe(2);
		valueOf(testRun, objAnim.getInterpolatorValues()[0]).shouldBe(1);
		valueOf(testRun, objAnim.getInterpolatorValues()[1]).shouldBe(2);
		objAnim.setInterpolatorValues(3, 4);
		valueOf(testRun, objAnim.interpolatorValues).shouldBeArray();
		valueOf(testRun, objAnim.interpolatorValues.length).shouldBe(2);
		valueOf(testRun, objAnim.interpolatorValues[0]).shouldBe(3);
		valueOf(testRun, objAnim.interpolatorValues[1]).shouldBe(4);

		// target
		objAnim.target = targetObj;
		returnObj = objAnim.target;
		valueOf(testRun, returnObj).shouldBeObject();
		valueOf(testRun, returnObj.testVal).shouldBe(targetObj.testVal);
		returnObj = objAnim.getTarget();
		valueOf(testRun, returnObj).shouldBeObject();
		valueOf(testRun, returnObj.testVal).shouldBe(targetObj.testVal);
		objAnim.setTarget(anotherTargetObj);
		returnObj = objAnim.target;
		valueOf(testRun, returnObj).shouldBeObject();
		valueOf(testRun, returnObj.testVal).shouldBe(anotherTargetObj.testVal);

		// startDelay
		objAnim.startDelay = 2000;
		valueOf(testRun, objAnim.startDelay).shouldBe(2000);
		valueOf(testRun, objAnim.getStartDelay()).shouldBe(2000);
		objAnim.setStartDelay(3000);
		valueOf(testRun, objAnim.startDelay).shouldBe(3000);

		finish(testRun);

	};

};
