var Sol = function (p) {
    "use strict";

    this.points = [];

    this.pointDistance = 20;

    this.generate(p);
};

Sol.prototype.generate = function (p) {
    "use strict";
    var index = 0;

    // p.noise(X)
    for (index = 0; index <= 50; index += 1) {
        // tab.push(valeur);
        this.points.push(p.noise(index * 0.02));
    }
};

Sol.prototype.draw = function (p) {
    "use strict";

    var index;
    for (index = 0; index < this.points.length; index += 1) {
        // index
        // points[index]
        p.line(index * this.pointDistance, this.points[index] * p.height * 0.7 + p.height * 0.3, (index + 1) * this.pointDistance, this.points[index + 1]  * p.height * 0.7 + p.height * 0.3);
    }
};
