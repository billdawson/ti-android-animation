exports.run = function() {
	var w = Ti.UI.createWindow({
			backgroundColor: "white",
			title: "Animate Background Color",
			fullscreen: false
		}),
		view1 = Ti.UI.createView({
			backgroundColor: "red",
			top: "16dp", left: "32dp",
			right: "32dp", height: "80dp"
		}),
		view2 = Ti.UI.createView({
			backgroundColor: "blue",
			top: "112dp", left: "32dp",
			right: "32dp", height: "80dp"
		}),
		button = Ti.UI.createButton({
			top: "208dp", left: "32dp", width: "150dp",
			height: "48dp", title: "Animate"
		}),
		animMod = require("com.billdawson.timodules.animation"),
		animator1 = animMod.objectAnimator.ofInt(view1, "backgroundColor",
												 "green"),
		animator2 = animMod.objectAnimator.ofInt(view2, "backgroundColor",
												 "yellow"),
		animatorSet = animMod.createAnimatorSet();

	w.add(button);
	w.add(view1);
	w.add(view2);
	animatorSet.playTogether([animator1, animator2]);
	animatorSet.setDuration(2000);
	animator1.setEvaluator(animMod.ARGB_EVALUATOR);
	animator2.setEvaluator(animMod.ARGB_EVALUATOR);
	animator1.setRepeatMode(animMod.REVERSE);
	animator2.setRepeatMode(animMod.REVERSE);
	animator1.setRepeatCount(1);
	animator2.setRepeatCount(1);

	button.addEventListener("click", function() {animatorSet.start();});
	
	w.open();
};
