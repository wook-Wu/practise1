window.bgcount = 15

function next()
{
	n = Math.floor(Math.random()*window.bgcount)
	$("#bg").css("background-image","url(./img/bg"+n+".jpg)");
	console.log("bg " + n);
}

function refershCountDown()
{
	//1608307200
	timeleft = 1583337600 - Math.round(new Date() / 1000);
	console.log(Math.round(new Date() / 1000))
	if(timeleft<0)
	{
		$("#t_d").text("你")
		$("#t_h").text("真")
		$("#t_m").text("厉")
		$("#t_s").text("害")
		return
	}
	// console.log(timeleft)
	d = Math.floor(timeleft/(24*60*60))
	h = Math.floor((timeleft%(24*60*60))/(60*60))
	m = Math.floor((timeleft%(60*60))/60)
	s = Math.floor(timeleft%60)
	// console.log(d+"-"+h+"-"+m+"-"+s)
	$("#t_d").text(""+(d+1))
	$("#t_h").text(""+h)
	$("#t_m").text(""+m)
	$("#t_s").text(""+s)
	$("#cutbox1").css("top",120-h*120/24+"px")
	$("#cd1").css("top",h*120/24-120+"px")
	$("#cutbox2").css("top",120-m*120/60+"px")
	$("#cd2").css("top",m*120/60-120+"px")
	$("#cutbox3").css("top",120-s*120/60+"px")
	$("#cd3").css("top",s*120/60-120+"px")
}

setInterval("next()",20000);
(setInterval("refershCountDown()",1000));