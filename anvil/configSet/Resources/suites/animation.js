module.exports = new function() {
	var finish;
	var valueOf;
	this.init = function(testUtils) {
		finish = testUtils.finish;
		valueOf = testUtils.valueOf;
	}

	this.options = {
		forceBuild: true
	}

	this.name = "animation";
	this.tests = [
		{name: "animation"}
	]

	this.animation = function(testRun) {
		var anim = require("com.billdawson.timodules.animation");
		Ti.API.info("I'M  IN MY TEST AND ANIM IS " + anim);
		finish(testRun);
	};

}
