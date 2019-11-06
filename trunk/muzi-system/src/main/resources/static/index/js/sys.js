var basePath="/basePrj/";
var prjPath="/basePrj/"; 

//$( function() {
//	cklogin() ;
//});

/**
 * 获取url中参数
 * @param name
 * @returns
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

/**
 * 加载字典
 * @param type	字典名称
 * @param value 字典值
 * @returns
 */
function loadDict(type,value){
	var returnStr = "";
	$.ajax({
		url:basePath + "common/dict/getName?type=" + type + "&value=" + value,
		type:'get',
		async: false,
		success:function(r){
			if (r.code == 0) {
				returnStr = r.data;
             } else {
                 layer.msg(r.msg);
             }
		},
		error:function(r){
			layer.msg("字典【"+dictname+"】加载错误");
		}
	});
	return returnStr;
}

/**
 * 下拉列表加载字典
 * @param id   select标签ID
 * @param type 字典名称
 * @returns
 */
function loadDicts(id,type){
	$.ajax({
        url: basePath + "common/dict/getDictsByType?type=" + type,
        type: "get",
        success: function (data) {
        	var htmlStr = "";
            if (data.code == 0) {
        		for(var key in data.data){
                	htmlStr += "<option value='" + key + "'>" +data.data[key]+ "</option>";
    			}
        		$("#"+id).append(htmlStr);
            } else {
                layer.msg(data.msg);
            }
        }
    });
}

//检查登录
function cklogin() {
    $.ajax({
        type: "POST",
        url: basePath+"cklogin",
        success: function (r) {
             if (r.code == 0) {
                 
            } else {
                //layer.msg(r.msg);
                //$("body").show();
            	top.location.href = prjPath; 
            }
        },
    });
}

//退出系统
function loginOut(){
	 $.ajax({
	        type: "GET", 
	        url: basePath+"logout",
	        success: function (r) {
	        	
	             if (r.code == 0) {
	            	 top.location.href = prjPath;   
	            } else {
	                //layer.msg(r.msg);
	                //$("body").show();
	            	top.location.href = prjPath; 
	            }
	        },
	    });
}


Date.prototype.Format = function (fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
	if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}



//身份证验证
function isIdCard(num) {
	var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
	var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
	var varArray = new Array();
	var intValue;
	var lngProduct = 0;
	var intCheckDigit;
	var intStrLen = num.length;
	var idNumber = num;
	if ((intStrLen != 15) && (intStrLen != 18)) {
		return false;
	}
	// check and set value
	for (i = 0; i < intStrLen; i++) {
		varArray[i] = idNumber.charAt(i);
		if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
			return false;
		} else if (i < 17) {
			varArray[i] = varArray[i] * factorArr[i];
		}
	}
	if (intStrLen == 18) {
		//check date
		var date8 = idNumber.substring(6, 14);
		if (isDate8(date8) == false) {
			return false;
		}
		// calculate the sum of the products
		for (i = 0; i < 17; i++) {
			lngProduct = lngProduct + varArray[i];
		}
		// calculate the check digit
		intCheckDigit = parityBit[lngProduct % 11];
		// check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }else {        //length is 15
        //check date
    	var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {
            return false;
        }
    }
    return true;
}


function isDate6(sDate) {
	if(!/^[0-9]{6}$/.test(sDate)) {
		return false;
	}
	var year,month, day;
	year =sDate.substring(0, 4);month =sDate.substring(4, 6);
	if (year< 1700 || year > 2500) return false
	if (month< 1 || month > 12) return false
	return true
}

function isDate8(sDate) {
	if(!/^[0-9]{8}$/.test(sDate)) {
		return false;
	}
	var year,month, day;
	year =sDate.substring(0, 4);
	month =sDate.substring(4, 6);
	day =sDate.substring(6, 8);
	var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31]
	if (year< 1700 || year > 2500) return false
	if (((year %4 == 0) && (year % 100 != 0)) ||(year % 400 == 0)) iaMonthDays[1] = 29;
	if (month< 1 || month > 12) return false
	if (day< 1 || day > iaMonthDays[month - 1])return false
	return true
}


