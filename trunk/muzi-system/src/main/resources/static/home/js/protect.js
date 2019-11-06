document.onkeydown=function(){
    var e = window.event||arguments[0];
    if(e.keyCode==123){
    	alert("看不到~~~~~·")
            return false;
    }else if((e.ctrlKey)&&(e.shiftKey)&&(e.keyCode==73)){
    	alert("看不到~~~~~·")
            return false;
    }else if((e.ctrlKey)&&(e.keyCode==85)){
           alert("看不到~~~~~·")
            return false;
    }else if((e.ctrlKey)&&(e.keyCode==83)){
           alert("看不到~~~~~·")
           return false;
    }
}
document.oncontextmenu=function(){
	alert("看不到~~~~~·")
    return false;
}