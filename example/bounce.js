exports.run = function() {
	var w = Ti.UI.createWindow({
			backgroundColor: "white",
			title: "Rotate and Bounce",
			fullscreen: false
		}),
		view1 = Ti.UI.createView({
			backgroundColor: "red",
			top: "16dp", left: "32dp",
			right: "32dp", height: "280dp"
		}),
		view2 = Ti.UI.createView({
			backgroundColor: "blue",
			top: "0dp", left: "0dp",
			width: "30dp", height: "30dp"
		}),
		view3 = Ti.UI.createView({
			backgroundColor: "blue",
			top: "0dp", right: "0dp",
			width: "30dp", height: "30dp"
		}),
		button = Ti.UI.createButton({
			top: "312dp", left: "32dp", width: "150dp",
			height: "48dp", title: "Animate"
		}),
		animMod = require("com.billdawson.timodules.animation");

	w.add(button);
	w.add(view1);
	view1.add(view2);
	view1.add(view3);

	function runAnimation() {
		animMod.viewPropertyAnimator.animate(view2)
			.setInterpolator(animMod.BOUNCE_INTERPOLATOR)
			.setDuration(3000)
			.xBy("200dp")
			.yBy("250dp")
			.rotationBy(360 * 4)
			.withEndAction(function() {
				animMod.viewPropertyAnimator.animate(view2)
				.setDuration(800)
				.setInterpolator(animMod.LINEAR_INTERPOLATOR)
				.setStartDelay(2000)
				.xBy("-200dp")
				.yBy("-250dp")
				.rotationBy(-360*4);
			});
		animMod.viewPropertyAnimator.animate(view3)
			.setInterpolator(animMod.BOUNCE_INTERPOLATOR)
			.setDuration(3000)
			.xBy("-200dp")
			.yBy("250dp")
			.rotationBy(-360 * 4)
			.withEndAction(function() {
				animMod.viewPropertyAnimator.animate(view3)
				.setDuration(800)
				.setInterpolator(animMod.LINEAR_INTERPOLATOR)
				.setStartDelay(2000)
				.xBy("200dp")
				.yBy("-250dp")
				.rotationBy(360*4);
			});

	}
	
	button.addEventListener("click", runAnimation);
	w.open();
};
