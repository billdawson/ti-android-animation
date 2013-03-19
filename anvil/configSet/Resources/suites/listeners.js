module.exports = new function() {
	var finish,
		valueOf, 
		animMod = require("com.billdawson.timodules.animation");

	function makeWinAndView(openCallback) {
		var win = Ti.UI.createWindow(),
			view = Ti.UI.createView();
		win.add(view);
		win.addEventListener("open", openCallback);
		return {view: view, win: win};
	}

	this.init = function(testUtils) {
		finish = testUtils.finish;
		valueOf = testUtils.valueOf;
	};

	this.options = {
		forceBuild: true
	};

	this.name = "listeners";
	this.tests = [
		{name: "objectAnimatorStart"},
		{name: "objectAnimatorEnd"},
		{name: "objectAnimatorCancel", timeout: 10000},
		{name: "objectAnimatorRepeat"},
		{name: "viewPropertyAnimatorListener"},
		{name: "viewPropertyAnimatorStartAction"},
		{name: "viewPropertyAnimatorEndAction"},
		{name: "animatorSetStart"},
		{name: "animatorSetEnd"},
		{name: "animatorSetCancel", timeout: 10000}

	];

	this.objectAnimatorStart = function(testRun) {
		var ui;
		function test() {
			var oa = animMod.objectAnimator.ofFloat(ui.view, "translationX", 5);
			oa.addEventListener("start", function(e) {
				valueOf(testRun, e.type).shouldBe("start");
				valueOf(testRun, "" + e.source).shouldBe("[object ObjectAnimator]");
				finish(testRun);
			});
			oa.start();
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.objectAnimatorEnd = function(testRun) {
		var ui;
		function test() {
			var oa = animMod.objectAnimator.ofFloat(ui.view, "translationX", 5);
			oa.addEventListener("end", function(e) {
				valueOf(testRun, e.type).shouldBe("end");
				valueOf(testRun, "" + e.source).shouldBe("[object ObjectAnimator]");
				finish(testRun);
			});
			oa.start();
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.objectAnimatorCancel = function(testRun) {
		var ui;
		function test() {
			var oa = animMod.objectAnimator.ofFloat(ui.view, "translationX", 5);
			oa.setDuration(5000);
			oa.addEventListener("cancel", function(e) {
				valueOf(testRun, e.type).shouldBe("cancel");
				valueOf(testRun, "" + e.source).shouldBe("[object ObjectAnimator]");
				finish(testRun);
			});
			oa.start();
			setTimeout(function(){oa.cancel();}, 1000);
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.objectAnimatorRepeat = function(testRun) {
		var ui;
		function test() {
			var oa = animMod.objectAnimator.ofFloat(ui.view, "translationX", 5);
			oa.setRepeatCount(1);
			oa.setRepeatMode(animMod.RESTART);
			oa.addEventListener("repeat", function(e) {
				valueOf(testRun, e.type).shouldBe("repeat");
				valueOf(testRun, "" + e.source).shouldBe("[object ObjectAnimator]");
				finish(testRun);
			});
			oa.start();
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.viewPropertyAnimatorListener = function(testRun) {
		var ui,
			gotStart = false,
			gotEnd = false;

		function test() {
			animMod.viewPropertyAnimator.animate(ui.view).setListener(function(e) {
				valueOf(testRun, "" + e.source).shouldBe("[object ViewPropertyAnimator]");
				gotStart = gotStart || e.type === 'start';
				gotEnd = gotEnd || e.type === 'end';
				if (gotStart && gotEnd) {
					finish(testRun);
				}
			}).translationX(5);
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.viewPropertyAnimatorStartAction = function(testRun) {
		var ui;
		function startAction(e) {
			valueOf(testRun, e.type).shouldBe("start");
			valueOf(testRun, "" + e.source).shouldBe("[object ViewPropertyAnimator]");
			finish(testRun);
		}

		function test() {
			animMod.viewPropertyAnimator.animate(ui.view).withStartAction(startAction).translationX(5);
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.viewPropertyAnimatorEndAction = function(testRun) {
		var ui;
		function endAction(e) {
			valueOf(testRun, e.type).shouldBe("end");
			valueOf(testRun, "" + e.source).shouldBe("[object ViewPropertyAnimator]");
			finish(testRun);
		}

		function test() {
			animMod.viewPropertyAnimator.animate(ui.view).withEndAction(endAction).translationX(5);
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.animatorSetStart = function(testRun) {
		var ui;
		function test() {
			var children = [
				animMod.objectAnimator.ofFloat(ui.view, "translationX", 5),
				animMod.objectAnimator.ofFloat(ui.view, "translationY", 5)
			];

			var as = animMod.createAnimatorSet();
			as.addEventListener("start", function(e) {
				valueOf(testRun, e.type).shouldBe("start");
				valueOf(testRun, "" + e.source).shouldBe("[object AnimatorSet]");
				finish(testRun);
			});
			as.playSequentially(children);
			as.start();
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.animatorSetEnd = function(testRun) {
		var ui;
		function test() {
			var children = [
				animMod.objectAnimator.ofFloat(ui.view, "translationX", 5),
				animMod.objectAnimator.ofFloat(ui.view, "translationY", 5)
			];

			var as = animMod.createAnimatorSet();
			as.addEventListener("end", function(e) {
				valueOf(testRun, e.type).shouldBe("end");
				valueOf(testRun, "" + e.source).shouldBe("[object AnimatorSet]");
				finish(testRun);
			});
			as.playSequentially(children);
			as.start();
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

	this.animatorSetCancel = function(testRun) {
		var ui;
		function test() {
			var children = [
				animMod.objectAnimator.ofFloat(ui.view, "translationX", 5),
				animMod.objectAnimator.ofFloat(ui.view, "translationY", 5)
			];

			var as = animMod.createAnimatorSet();
			as.setDuration(10000);
			as.addEventListener("cancel", function(e) {
				valueOf(testRun, e.type).shouldBe("cancel");
				valueOf(testRun, "" + e.source).shouldBe("[object AnimatorSet]");
				finish(testRun);
			});
			as.playSequentially(children);
			as.start();
			setTimeout(function(){as.cancel();}, 1000);
		}
		ui = makeWinAndView(test);
		ui.win.open();
	};

};
