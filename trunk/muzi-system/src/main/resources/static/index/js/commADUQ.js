/* 通用增删改查js */

/**
 * 初始化工具
 * @param path
 * @returns
 */
function initADUQ(path){
	$("body").load(prjPath+"html/demo/demo.html");
}

//组件
function queryUtils(config) {
	//参数 表名
	this.table = config.table || "" ;
	this.tableId = config.tableId || "id" ;
	//列 定义
	this.cols = config.cols || [];
	this.addCols = config.addCols || [];
	
	//查询条件
	this.search = config.search || "";
	//下拉框过滤
	this.select = config.select || "" ;
	
	//元素id定义
	this.ids = config.ids || { "tab" : "tabList" ,"addBtn" : "addBtn" ,"searchBox" : "searchBox" , "searchBtn" : "searchBtn" ,"selectBox": "selectBox" } ;
	//编辑按钮定义
	this.btns = config.btns || ["add","update" , "del" ,"info" ]; 
	this.btnsMore = config.btnsMore || null;  
	
	console.log( config.btns );
	console.log( this.btns );
	//url 基本不变,也可指定自定义的url
	this.url = {
			list : config.url.list || "/common/demo/list",
			add : config.url.add || "/common/demo/add",
			update : config.url.update || "/common/demo/update",
			del : config.url.del || "/common/demo/del",
			search : config.url.search || "/common/demo/search",
			info : config.url.search || "/common/demo/info",
			select : config.url.select || "/common/demo/select",
	};
	
	
	
	//事件绑定
	this.init = function(){
		//绑定搜索
		var obj = this;
		
		if( obj.search == "" ){
			$("#"+obj.ids["searchBox"]).hide();
			$("#"+obj.ids["searchBtn"]).hide();
		}else{
			$("#"+obj.ids["searchBox"]).show();
			$("#"+obj.ids["searchBtn"]).show();
			
			//点击搜索按钮 
			$("#"+obj.ids.searchBtn).on("click",function(){
				$('#'+obj.ids["tab"] ).bootstrapTable('refresh', {
						query : {searchValue :  $("#"+obj.ids["searchBox"]).val() ,
						limit	:10,
						offset : 0 
						}    
				} );
			}); 
			
			//回车输入框
			$("#"+obj.ids.searchBox).on("keydown",function(e){
				if(13 == e.keyCode) 
					$('#'+obj.ids["tab"] ).bootstrapTable('refresh', {
						query : {searchValue :  $("#"+obj.ids["searchBox"]).val() , }    
					} );
			});
		}
		
		//加载列表
		obj.loadList();
		
		//加载类别
		if(obj.select != "") {
			$("#"+obj.ids.selectBox).show(); 
			
			//如果是数据字典
			if(obj.select.selectType == "dict"){
				loadDicts(obj.ids.selectBox , obj.select. dictType );
			}else if(obj.select.selectType == "table"){
				//使用本表
				obj.loadSelect(); 
			}else{
				//自定义
				obj.select.fn(  $("#"+obj.ids.selectBox) );
			}
			//下拉框的事件 
			$("#"+obj.ids.selectBox).on("change",function(){
				//alert( obj.select.field );
				var opt = {
						query : { "type" :   obj.select.field  ,  "typeValue" : $("#"+obj.ids.selectBox).val()   }     
				}
				$('#'+obj.ids["tab"] ).bootstrapTable('refresh', opt); 
			}); 
			
		}else{
			$("#"+obj.ids.selectBox).hide(); 
		}
		
		//添加数据
		if( obj.btns.join(",").indexOf("add,") != -1 || obj.btns.join(",").indexOf(",add,") != -1 || obj.btns.join(",").indexOf(",add") != -1 ){
			$("#"+obj.ids.addBtn).show(); 
			
			var html = obj.initAddDiv(); 
			//添加按钮 
			$("body").on("click",".add_btn_submit",function(){
				//alert("add="+vals ); 
				obj.addData();
			});
			
			//添加事件
			$("#"+obj.ids.addBtn).on("click",function(){
				//alert("add ");
				layer.open({ 
					type : 1,
					title : '增加', 
					maxmin : true,
					shadeClose : false, // 点击遮罩关闭层
					area : [ '800px', '520px' ],
					content : html   // iframe的url 
				});
				
			}); 
		}else{
			//隐藏
			$("#"+obj.ids.addBtn).hide(); 
			//alert(111); 
		}
		
		//更新按钮 
		$("body").on("click",".update_btn_submit",function(){
			//alert("add="+vals ); 
			obj.updateData( obj._tableIdValue ); 
		});
		
		//编辑事件 
		$("body").on("click",".edit_btn",function(){
			//alert( $(this).attr("tag") ); 
			//obj.addData();
			obj._tableIdValue = $(this).attr("tag") ;
			var html = obj.initAddDiv("update"); 
			obj.loadEditInfo( $(this).attr("tag") );
			layer.open({
				type : 1,
				title : '修改', 
				maxmin : true,
				shadeClose : false, // 点击遮罩关闭层
				area : [ '800px', '520px' ], 
				content : html   // iframe的url 
			});
		});
		
		//查看事件
		$("body").on("click",".show_btn",function(){
			obj._tableIdValue = $(this).attr("tag") ;
			var html = obj.initAddDiv("show"); 
			obj.loadEditInfo( $(this).attr("tag") );
			layer.open({
				type : 1,
				title : '查看', 
				maxmin : true,
				shadeClose : false, // 点击遮罩关闭层
				area : [ '800px', '520px' ], 
				content : html   // iframe的url 
			});
		});
		
		//删除事件
		$("body").on("click",".del_btn",function(){
			//alert( $(this).attr("tag") ); 
			//obj.addData();
			obj._tableIdValue = $(this).attr("tag") ;
			//询问框
			layer.confirm('确认删除id为'+obj._tableIdValue+'的数据？', {
			  btn: ['确认','取消'] //按钮
			}, function(){
			  //layer.msg('的确很重要', {icon: 1});
				var map =  {  
						_table : obj.table,
						_tableId : obj.tableId,
						_tableIdValue : obj._tableIdValue ,
					};
				$.ajax({
					url: basePath + obj.url. del ,
					type:"post", 
					dataType:"json",
					data: JSON.stringify( map ) ,  
					contentType : "application/json", 
					success:function(data){
						layer.msg("操作成功"); 
						layer.closeAll(); 
						obj.reloadList();   
					},
					error:function(){
						
					}
				});
				
			}, function(){
			  
			});
			
			
		});
		
	}

			
	//加载下拉框
	this.loadEditInfo = function(id){
		var obj = this;
		var cols2 = "";
		var cols = obj.addCols;
		for (var i = 0; i < cols.length; i++) {
			if(cols[i][2] == "addEdit"){
				if(i>0) cols2+= ",";
				cols2+= cols[i][0];
			}
		}
		
		var map =  {  
			//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
			limit : 1,
			offset : 0,
			table : obj.table,
			cols : cols2,
			searchName:  obj.tableId , 
			searchValue : id ,
		};
		//查询 id 对应详情
		$.ajax({
			url: basePath + obj.url. list ,
			type:"post", 
			dataType:"json",
			data: JSON.stringify( map ) ,  
			contentType : "application/json", 
			success:function(data){
					//$("#"+obj.ids.selectBox).html("<option value=''>请选择</option>"); 
					var info = data.rows[0]; 
					for (var i = 0; i < cols.length; i++) {
						if(cols[i][2] == "addEdit"){
							$("#field_"+cols[i][0]).val(  info[ cols[i][0] ] );
						}
					}
			},
			error:function(){
				
			}
		});
	}		
	
	//重新加载列表 
	this.reloadList = function(){
		var obj = this;
		$('#'+obj.ids["tab"] ).bootstrapTable('refresh', {
			
		} );
	};
	
	//方法,加载列表  
	this.loadList = function(){
		var obj = this;
		commLoadList(obj.ids["tab"] ,obj.url.list,obj.ids["searchBox"],obj.table,obj.cols ,obj.search ,obj.tableId ,obj.btns ,obj.btnsMore ); 
	}
	
	//加载下拉框
	this.loadSelect = function(){
		var obj = this;
		$.ajax({
			url: basePath + obj.url. select ,
			data: { "select" : obj.select.value , "selectTitle" : obj.select.title , "table" : obj.select.table }, 
			type:"get", 
			dataType:"json",
			success:function(data){
				if(data.status == "success"){
					$("#"+obj.ids.selectBox).html("<option value=''>"+obj.select.selectTitle+"</option>");  
					var list = data.list; 
					for (var i = 0; i < list.length; i++) {
						$("#"+obj.ids.selectBox).append("<option value='"+list[i][obj.select.value]+"'>"+list[i][obj.select.title] +"</option>");
					} 
				}else{
					layer.msg(data.msg);
				}
			},
			error:function(d,msg){
				layer.msg( "下拉框的值加载失败："+msg);
			}
		});
	}
	
	//加载下拉 输入框的值
	this.loadSelectInput = function(selectObj){
		var obj = this;
		var html = null;
		$.ajax({
			url: basePath + obj.url. select ,
			data: { "select" : selectObj.value , "selectTitle" : selectObj.title , "table" : selectObj.table }, 
			type:"get", 
			dataType:"json",
			async : false,
			success:function(data){
				if(data.status == "success"){
					//$("#"+obj.ids.selectBox).html("<option value=''>"+selectObj.selectTitle+"</option>");  
					html = "<option value=''>"+selectObj.selectTitle+"</option>" ;
					var list = data.list; 
					for (var i = 0; i < list.length; i++) {
						//$("#"+obj.ids.selectBox).append("<option value='"+list[i][selectObj.value]+"'>"+list[i][selectObj.title] +"</option>");
						html += "<option value='"+list[i][selectObj.value]+"'>"+list[i][selectObj.title] +"</option>" ;
					} 
				}else{
					layer.msg(data.msg);
				}
			},
			error:function(d,msg){
				layer.msg( "下拉框的值加载失败："+msg);
			}
		});
		return html ;
	}
	
	//添加div
	this.initAddDiv = function(update){
		var obj = this;
		var addCols = obj.addCols;
		var html = ""; 
		html += '<div class="row11" ><div class="col-sm-1211" style="padding: 15px; ">  <div class="card card-primary">   ' ;
		html += ' <form role="form" ><div class="card-body"> ' ;
		
		var selectObj = null;
		for (var i = 0; i < addCols.length; i++) {
			selectObj = addCols[i][3];
			//alert( selectObj.type ); 
			if( null == selectObj  || selectObj.type == "text" || selectObj.type == "number"  ){
				//layer.msg( selectObj.type );
				
				html += '<div class="form-group"><label for="field_'+addCols[i][0]+'">'+addCols[i][1]+'</label> '+
	                ' <input type="'+selectObj.type+'" maxlength="'+selectObj["max"]+'" minlength="'+selectObj["min"]+'" class="form-control" '+(update == "show" ? ' readonly="readonly" ' : '' )+'  aria-required="true" aria-invalid="true" id="field_'+addCols[i][0]+'" placeholder="请输入'+addCols[i][1]+'..."> '+
	              '</div>' ; 
			}else{
				//text=文本，bigtext=长文本 , num=数字 ,date=日期 ，select=下拉框()，choose=自定义选择器
				//type : "text" ,min : 2 ,max:20
				if( selectObj.type == "bigtext" ){
					html += '<div class="form-group"><label for="field_'+addCols[i][0]+'">'+addCols[i][1]+'</label> '+
	                ' <textarea  rows="5" type="'+selectObj.type+'" maxlength="'+selectObj["max"]+'" minlength="'+selectObj["min"]+'" class="form-control" '+(update == "show" ? ' readonly="readonly" ' : '' )+'  aria-required="true" aria-invalid="true" id="field_'+addCols[i][0]+'" placeholder="请输入'+addCols[i][1]+'..."> </textarea>'+
	              '</div>' ; 
				}else if(selectObj.type == "select"){
					//table  other
					if("table" == selectObj.selectType){
						//加载表数据 
						console.log ( selectObj );
						;
						html += '<div class="form-group"><label for="field_'+addCols[i][0]+'">'+addCols[i][1]+'</label> '+
		                ' <select class="form-control" type="'+selectObj.type+'" maxlength="'+selectObj["max"]+'" minlength="'+selectObj["min"]+'" class="form-control" '+(update == "show" ? ' readonly="readonly" ' : '' )+'  aria-required="true" aria-invalid="true" id="field_'+addCols[i][0]+'" placeholder="请输入'+addCols[i][1]+'...">'
		                +obj.loadSelectInput( selectObj )+
		                ' </select>'+
		              '</div>';
						
					}else{
						html += '<div class="form-group"><label for="field_'+addCols[i][0]+'">'+addCols[i][1]+'</label> '+
		                ' <select class="form-control" type="'+selectObj.type+'" maxlength="'+selectObj["max"]+'" minlength="'+selectObj["min"]+'" class="form-control" '+(update == "show" ? ' readonly="readonly" ' : '' )+'  aria-required="true" aria-invalid="true" id="field_'+addCols[i][0]+'" placeholder="请输入'+addCols[i][1]+'...">'
		                +selectObj.select()+
		                ' </select>'+
		              '</div>';
						
					}
				}else if( selectObj.type == "date" ){ 
					html += '<div class="form-group"><label for="field_'+addCols[i][0]+'">'+addCols[i][1]+'</label> '+
	                ' <input type="'+selectObj.type+'" maxlength="'+selectObj["max"]+'" minlength="'+selectObj["min"]+'" class="form-control" '+(update == "show" ? ' readonly="readonly" ' : '' )+'  aria-required="true" aria-invalid="true" id="field_'+addCols[i][0]+'" placeholder="请输入'+addCols[i][1]+'..."> '+
	              '</div>' ; 
				}else if( selectObj.type == "choose" ){ 
					html += '<div class="form-group"><label for="field_'+addCols[i][0]+'">'+addCols[i][1]+'</label> '+    
	                ' <input type="text" maxlength="'+selectObj["max"]+'" minlength="'+selectObj["min"]+'"  '+(update == "show" ? ' readonly="readonly" ' : ' onfocus="'+selectObj.choose+'( \'field_'+addCols[i][0]+'\' ); " ' )+'    class="form-control"  readonly="readonly"  aria-required="true" aria-invalid="true" id="field_'+addCols[i][0]+'" placeholder="请点击选择输入'+addCols[i][1]+'..."> '+
	              '</div>' ; 
				}
			}
			
		}
		
		html += '</div>' ;  
		if(update == "update")  
			html += ' <div class="card-footer"> <button type="button" class="btn btn-primary update_btn_submit" >更新</button></div></form> ';
		else if(update == "show")  
			; 
		else
			html += ' <div class="card-footer"> <button type="button" class="btn btn-primary add_btn_submit" >新增</button></div></form> ';
		html += '</div> </div></div>';
		return html;
	} ;
	
	//添加数据
	this.addData = function(){
		var obj = this; 
		var addCols = obj.addCols;
		var vals = {  "_addCols" : addCols , "_table" : obj.table  };   
		var addColsStr = "";
		for (var i = 0; i < addCols.length; i++) {
			if( $("#field_"+addCols[i][0]).val() == "" ){
				$("#field_"+addCols[i][0]).focus();
				return;
			} 
			//自定义校验
			if( addCols[i][3].check != null ){
				//$("#field_"+addCols[i][0]).focus();
				var ok = addCols[i][3].check ( $("#field_"+addCols[i][0]).val() );
				if( "ok" != ok ){
					$("#field_"+addCols[i][0]).focus();  
					layer.msg(ok); 
					return; 
				}
					
			} 
			vals [ addCols[i][0] ] =  $("#field_"+addCols[i][0]).val();
		}
		//添加数据 
		$.ajax({
			url: basePath + obj.url. add  ,
			data: JSON.stringify( vals ) , 
			type:"post",  
			dataType:"json",
			contentType : "application/json", 
			success:function(data){
				if(data.status == "success"){
					//alert("添加成功！");
					layer.msg("操作成功"); 
					layer.closeAll(); 
					obj.reloadList();  
				}else{
					layer.msg("操作失败："+data.msg); 
				}
			},
			error:function(d,msg){
				layer.msg("操作失败："+msg); 
			}
		});
		
	};
	
	
	//更新数据
	this.updateData = function(id){
		var obj = this; 
		var addCols = obj.addCols;
		var vals = {  "_addCols" : addCols , "_table" : obj.table , "_tableId" :  obj.tableId , "_tableIdValue" : id };   
		var addColsStr = "";
		for (var i = 0; i < addCols.length; i++) {
			if(addCols[i][2] =="addEdit" ){
				if( $("#field_"+addCols[i][0]).val() == ""   ){
					$("#field_"+addCols[i][0]).focus();
					return;
				} 
				
				//自定义检查
				if( addCols[i][3].check != null ){
					//$("#field_"+addCols[i][0]).focus();
					var ok = addCols[i][3].check ( $("#field_"+addCols[i][0]).val() );
					if( "ok" != ok ){
						$("#field_"+addCols[i][0]).focus();  
						layer.msg(ok); 
						return; 
					}
				} 
				
				vals [ addCols[i][0] ] =  $("#field_"+addCols[i][0]).val();
			}
		}
		//添加数据 
		$.ajax({
			url: basePath + obj.url. update  ,
			data: JSON.stringify( vals ) , 
			type:"post",  
			dataType:"json",
			contentType : "application/json", 
			success:function(data){
				if(data.status == "success"){
					//alert("添加成功！");
					layer.msg("操作成功"); 
					layer.closeAll(); 
					obj.reloadList();  
				}else{
					layer.msg(data.msg); 
				}
			},
			error:function(d,msg){
				layer.msg("操作失败："+msg); 
			}
		});
		
	};
	
};

/**
 * 加载列表数据
 * @returns
 */
function commLoadList(tabId,url,searchBox,tab,cols,search,tableId,btns ,btnsMore) {
	//selectLoad();
	console.log( tabId+" , " +url );
	//重新定义列表
	var newColumns = [{
		checkbox : true
	}];
	//显示列 
	for (var i = 0; i < cols.length; i++) {
		newColumns.push({
			visible : cols[i] [2] == "show",
			field : cols[i] [0],
			title :cols[i] [1] ,
			formatter : cols[i] [3]  
		});
	} 
	//按钮
	newColumns.push(
		{
		title : '操作',
		field : tableId,
		align : 'center', 
		formatter : function(value, row, index) { 
			var e = '<a class="btn btn-primary btn-sm edit_btn" href="#" tag="'+row[tableId]+'"  mce_href="#" title="编辑" onclick111="edit(\''
				+ row[tableId]
				+ '\')"><i class="fa fa-edit"></i></a> ';
			var d = '<a class="btn btn-warning btn-sm del_btn" tag="'+row[tableId] +'"  href="#" title="删除"  mce_href="#" onclick111="remove(\''
				+ row[tableId]  
				+ '\')"><i class="fa fa-remove"></i></a> '; 
			var f = '<a class="btn btn-success btn-sm show_btn" tag="'+row[tableId] +'"  href="#" title="查看"  mce_href="#" onclick111="addD(\''
				+ '\')"><i class="fa fa-eye"></i></a> '; 
			var html = "";
			if( btns.join(",").indexOf("update,") != -1 || btns.join(",").indexOf(",update,") != -1  || btns.join(",").indexOf(",update") != -1 ){
				html += e;
			}
			if( btns.join(",").indexOf("del,") != -1 || btns.join(",").indexOf(",del,") != -1 || btns.join(",").indexOf(",del")  != -1 ){
				html += d; 
			}
			if( btns.join(",").indexOf("info,") != -1 || btns.join(",").indexOf(",info,") != -1 || btns.join(",").indexOf(",info")  != -1 ){
				html += f; 
			}
			//自定义按钮   
			if(btnsMore){
				
				for (var i = 0; i < btnsMore.length; i++) {
					html += btnsMore[i].fn(value, row, index);
				}
			}
			
			
			return html ;
		}
	} );
	//重新定义列表 end
	
	//tab
	$('#'+tabId)
		.bootstrapTable(
			{
				method : 'post', // 服务器数据的请求方式 get or post  
				url : basePath+ url , // 服务器数据的加载地址
				//	showRefresh : true,
				//	showToggle : true,
				//	showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数  
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					//return "limit="+params.limit+"&offset="+params.offset+"&type="+ $('#'+searchBox).val(); 
					var cols2 = "";
					for (var i = 0; i < cols.length; i++) {
						if(i>0) cols2+= ",";
						cols2+= cols[i][0];
					}
					var search1 = "";
					for (var i = 0; i < search.length; i++) {
						if(i>0) search1+= ",";
						search1+= search[i];
					}
					return {  
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						table : tab,
						cols : cols2,
						// name:$('#searchName').val(),
						searchName:  search1 , 
						searchValue : params.searchValue || "" ,
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : newColumns, 
	
			});
}