<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台(系统管理平台)</title>
<#include "common/header.ftl"/>
    <style>
        html, body {
            height: 100%;
        }

        /*body {
            background: #0f3854;
            background: -webkit-radial-gradient(center ellipse, #0a2e38 0%, #000000 70%);
            background: radial-gradient(ellipse at center, #0a2e38 0%, #000000 70%);
            background-size: 100%;
        }*/


        p{
            margin: 0;
            padding: 0;
            color: #0e0e0e;
        }



        #clock {
            font-family: 'Share Tech Mono', monospace;
            color: #ffffff;
            text-align: center;
            position: absolute;
            left: 50%;
            top: 50%;
            -webkit-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            color: #daf6ff;
            text-shadow: 0 0 20px #0aafe6, 0 0 20px rgba(10, 175, 230, 0);
        }
        #clock .time {
            letter-spacing: 0.05em;
            font-size: 80px;
            padding: 5px 0;
        }
        #clock .date {
            letter-spacing: 0.1em;
            font-size: 24px;
        }
        #clock .text {
            letter-spacing: 0.1em;
            font-size: 12px;
            padding: 20px 0 0;
        }
    </style>
</head>
<body>
	<div class="container">
		<#include "common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#include "common/menu.ftl" />
			</div>
			<div class="col-sm-9" id="clock" style="text-align: ">
				<p class="time">{{ time }}</p>
				<p class="date">{{ date }}</p>
			</div>
		</div>
	</div>

    <script type="text/javascript" src="js/vue.min.js"></script>
    <script>
        var clock = new Vue({
            el: '#clock',
            data: {
                time: '',
                date: ''
            }
        });

        var week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
        var timerID = setInterval(updateTime, 1000);
        updateTime();
        function updateTime() {
            var cd = new Date();
            clock.time = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
            clock.date = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth()+1, 2) + '-' + zeroPadding(cd.getDate(), 2) + ' ' + week[cd.getDay()];
        };

        function zeroPadding(num, digit) {
            var zero = '';
            for(var i = 0; i < digit; i++) {
                zero += '0';
            }
            return (zero + num).slice(-digit);
        }
    </script>
</body>
</html>