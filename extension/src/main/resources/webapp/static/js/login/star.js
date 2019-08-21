(function () {

	var Win_W=document.documentElement.clientWidth;
	var Win_H=document.documentElement.clientHeight;

	var oDiv=document.getElementById('star');
	var canvas=document.getElementById('canv');
	var ctx=canvas.getContext('2d');


	window.requestAnimationFrame =
	window.requestAnimationFrame || 
	window.mozRequestAnimationFrame || 
	window.webkitRequestAnimationFrame || 
	window.msRequestAnimationFrame;
	var timer=null;

	var starNum=500;
	var speedZ=3;
	var star=[];

	var X=Win_W/2;  //canvas中心X
	var Y=Win_H/2;  //canvas中心Y
	var Z=(Win_W+Win_H)/2;  //Z 目标点


	oDiv.style.width=Win_W+"px";
	oDiv.style.height=Win_H+"px";

	canvas.width=Win_W;
	canvas.height=Win_H;

	if (window.addEventListener) {
		window.addEventListener('resize', function () {
			Win_W=document.documentElement.clientWidth;
			Win_H=document.documentElement.clientHeight;
			oDiv.style.width=Win_W+"px";
			oDiv.style.height=Win_H+"px";
			canvas.width=Win_W;
			canvas.height=Win_H;
			X=Win_W/2;  //canvas中心X
			Y=Win_H/2;  //canvas中心Y
			Z=(Win_W+Win_H)/2;  //Z 参数最大值
			window.cancelAnimationFrame(timer);//取消动画
			initStar();
			step();	
		});
	}




initStar();
function initStar() {
	ctx.fillStyle="#000";
	ctx.strokeStyle="#fff";
	ctx.fillRect(0, 0, Win_W, Win_H);
	//初始化星星数据
	for (var i = 0; i < starNum; i++) {
		star[i]=[];
		//参数方程斜率x轴分量 Kx 		(-Win_W,Win_W)
		star[i][0]=Math.random()*(Win_W*2)-Win_W;
		//参数方程斜率y轴分量 Ky 		(-Win_H,Win_H)
		star[i][1]=Math.random()*(Win_H*2)-Win_H;
		//初始时参数 z 	(不同的初始参数z)
		star[i][2]=Math.random()*Z;
		//直线参数方程初始时 x
		star[i][3]=X+star[i][0]/(star[i][2]+speedZ)*200;
		//直线参数方程初始时 y
		star[i][4]=Y+star[i][1]/(star[i][2]+speedZ)*200;
	}
}


/*
直线参数方程,
y = y0 + Ky * 

x = x0 + Kx * 

*/




	
	step();
	function step() {

		ctx.fillRect(0, 0, Win_W, Win_H);
		for (var i = 0; i < starNum; i++) {				
			var preX=star[i][3];
			var preY=star[i][4];
			star[i][2]-=speedZ;
			var onOff=true;
			if (star[i][2]>Z) {
				star[i][2]-=Z;
				star[i][0]=Math.random()*(Win_W*2)-Win_W;
				star[i][1]=Math.random()*(Win_H*2)-Win_H;
				onOff=false;
			}
			if (star[i][2]<0) {
				star[i][2]+=Z;
				star[i][0]=Math.random()*(Win_W*2)-Win_W;
				star[i][1]=Math.random()*(Win_H*2)-Win_H;				
				onOff=false;
			}



			star[i][3]=X+star[i][0]/star[i][2]*200;
			star[i][4]=Y+star[i][1]/star[i][2]*200;


			if (onOff) {
				ctx.beginPath();
				ctx.lineWidth=(1-star[i][2]/Z)*2;
				ctx.moveTo(preX, preY);
				ctx.lineTo(star[i][3], star[i][4]);
				ctx.stroke();
				ctx.closePath();
			}

		}
		timer=requestAnimationFrame(arguments.callee);
	}



})();


function MenuInOut($obj) {
    this.$obj = $obj;
}

MenuInOut.prototype.init = function() {
    var that = this;
    this.$obj.on('mouseenter', function(event) {
        event.preventDefault();
        var tb = that.judegTB(event.pageY);
        if (tb) {
            that.$obj.attr("class", "b");
        } else {
            that.$obj.attr("class", "t");
        }
    });

    this.$obj.on('mouseleave', function(event) {
        event.preventDefault();
        var tb = that.judegTB(event.pageY);
        if (tb) {
            that.$obj.attr("class", "b_out");
        } else {
            that.$obj.attr("class", "t_out");
        }
    });
};

MenuInOut.prototype.judegTB = function(top) {
    var mid = this.$obj.offset().top + this.$obj.height() / 2;
    console.log(mid, top);
    if (top > mid) {
        return true; //鼠标在下
    } else {
        return false; //鼠标在上
    }
};

for (var i = 0; i < $('#menu_list a').length; i++) {
    new MenuInOut($('#menu_list a').eq(i)).init();
}


$('#menu_btn').on('click', function() {
    if (this.onOff) {
        this.onOff = false;
        $(this).removeClass('active');
        $('#menu').removeClass('active');

        clearTimeout($('#menu')[0].timer);
        $('#menu')[0].timer = setTimeout(function() {
            $('#menu').css('display', 'none');
        }, 500);
    } else {
        this.onOff = true;
        $(this).addClass('active');
        $('#menu').css('display', 'block');

        clearTimeout($('#menu')[0].timer);
        $('#menu')[0].timer = setTimeout(function() {
            $('#menu').addClass('active');
        }, 50);
    }
});

var urlArr = ['win10','player','PlantsVsZombies','baiduyun','m'];

//传进来的是 page
function Page($obj,url) {
    this.url = url||'#';
    this.$obj = $obj;
    this.$viewBtn = $obj.find('.view_case');
    this.$lines = $obj.find('.lines');
    this.$htxt = $obj.find('.h_section');
    this.go = true; //true 到左边去
    this.in = true; //true 从右边来
}

Page.prototype.init = function() {
    var that = this;
    this.$viewBtn.on('mouseenter', function() {
        that.$lines.addClass('active');
    });
    this.$viewBtn.on('mouseleave', function() {
        that.$lines.removeClass('active');
    });
    this.$viewBtn.on('click', function() {
        window.open(that.url,'_blank');
    });
};

Page.prototype.reset = function() {
    this.$viewBtn.removeClass('active');
    this.$htxt.removeClass('down');
    this.$htxt.addClass('up');
    this.$obj.removeClass('toLeft');
    this.$obj.removeClass('toRight');
    this.$obj.removeClass('Right');
    this.$obj.removeClass('Left');
    this.$obj.removeClass('in');
    this.$obj.removeClass('out');
}

Page.prototype.goOut = function() {
    var that = this;
    that.reset();
    that.$viewBtn.removeClass('active');
    that.$htxt.removeClass('down');
    that.$htxt.addClass('up');
    that.$obj.addClass('out');
    if (that.go) {
        //到左边去
        that.$obj.addClass('toLeft');
    } else {
        that.$obj.addClass('toRight');
    }
}

Page.prototype.comeIn = function() {
    var that = this;
    that.reset();
    that.$viewBtn.addClass('active');
    that.$htxt.removeClass('up');
    that.$htxt.addClass('down');
    that.$obj.addClass('in');
    setTimeout(function() {
        that.$obj.removeClass('in');
    }, 400);

    if (that.in) {
        //从右边来
        //先把 this.$obj 移到右边
        that.$obj.addClass('Right');
        setTimeout(function() {
            that.$obj.removeClass('Right');
        }, 10);
    } else {
        console.log('left');
        that.$obj.addClass('Left');
        setTimeout(function() {
            that.$obj.removeClass('Left');
        }, 10);
    }

}

var pageArr = [];
pageArr[0] = {
    $obj: $('#page1'),
    go: true,
    in : true,
    comeIn: function() {
        var that = this;
        this.$obj.removeClass('toLeft');
        this.$obj.removeClass('toRight');
        if (this.in) {
            this.$obj.addClass('Right');
            setTimeout(function() {
                that.$obj.removeClass('Right');
            }, 20);
        } else {
            this.$obj.addClass('Left');
            setTimeout(function() {
                that.$obj.removeClass('Left');
            }, 20);
        }
    },
    goOut: function() {
        if (this.go) {
            this.$obj.addClass('toLeft');
        } else {
            this.$obj.addClass('toRight');
        }
    }
};


pageArr[1] = new Page($('#page2'),urlArr[0]);
pageArr[1].init();
pageArr[2] = new Page($('#page3'),urlArr[1]);
pageArr[2].init();
pageArr[3] = new Page($('#page4'),urlArr[2]);
pageArr[3].init();
pageArr[4] = new Page($('#page6'),urlArr[4]);
pageArr[4].init();

/*pageArr[4] = new Page($('#page5'),urlArr[3]);
pageArr[4].init();
pageArr[5] = new Page($('#page6'),urlArr[4]);
pageArr[5].init();*/



var now = 0;
var changing = false;
var logining = true;

function nextFn() {
    if (changing) {
        return;
    }
    changFn();
    $('#hp_nav .next').removeClass('animat');
    setTimeout(function () {
        $('#hp_nav .next').addClass('animat');
    }, 10);

    pageArr[now].go = true;
    pageArr[now].goOut();
    now = (now + 1) % pageArr.length;
    pageArr[now].in = true;
    pageArr[now].comeIn();
}

$('#hp_nav .next').on('click', nextFn);

function prevFn() {
    if (changing) {
        return;
    }
    changFn();
    $('#hp_nav .prev').removeClass('animat');
    setTimeout(function () {
        $('#hp_nav .prev').addClass('animat');
    }, 10);
    pageArr[now].go = false;
    pageArr[now].goOut();
    now = (now - 1);
    if (now<0) {
        now =  pageArr.length-1;
    }
    pageArr[now].in = false;
    pageArr[now].comeIn();
}


$('#hp_nav .prev').on('click', prevFn);


function changFn () {
    changing=true;
    setTimeout(function () {
        changing=false;
    },1200);
}

var scrollOnOff=false;
// $(document).mousewheel(function(event, delta) {
//     if (logining) {
//         return;
//     }
//     if (scrollOnOff) {
//         return;
//     }
//     scrollOnOff = true;
//     setTimeout(function() {
//         scrollOnOff = false;
//     }, 150);
//     if (delta < 0) {
//         // document.title='下';
//         nextFn();
//     } else {
//         // document.title='上';
//         prevFn();
//     }
//
// });
//





// x.addEventListener("webkitAnimationEnd", myStartFunction);
// x.addEventListener("animationend", myStartFunction);


$('#login .login5').on('webkitAnimationEnd', function(event) {
    event.preventDefault();
    logining=false;
});

$('#login .login5').on('animationend', function(event) {
    event.preventDefault();
    $('#login').css('display','none');
    logining=false;
});

$(".btn-primary").addClass("btn-warning").removeClass("btn-primary");