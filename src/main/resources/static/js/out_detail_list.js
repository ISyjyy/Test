
jQuery(function($) {
	// 数据表格
	// 数据表格
	// 数据表格
	$('#querylist')
			.bootstrapTable(
					{
						// height : 400,
						columns : [
                            {
                                field : '',
                                title : '序号',
                                width : '5%',
                                formatter : function(value, row, index) {
                                    return index + 1;
                                }
                            },

								{
									field : 'name',
									title : '姓名',
									width : '5%'
								},
								{
									field : 'password',
									title : '密码',
									width : '6%'
								}],
						method : 'get',
						striped : true,
						dataType : "json",
						pagination : true,
						queryParamsType : "limit",
						singleSelect : false,
						contentType : "application/json",
						pageSize : 10,
						pageNumber : 1,
						pageList : [20,50],
						search : false, // 不显示 搜索框
						showColumns : false, // 不显示下拉框（选择显示的列）
						escape : true, // 字符转义
						sidePagination : "server", // 服务端请求
						queryParams : queryParams,
						responseHandler : responseHandler,
						url : "queryUser.do",
						// 数据显示完毕后事件
						onLoadSuccess : function(data) {
							// 隐藏加载框
							$.loading.end();
							// 初始化窗口函数
							// $(".save").mzDialog();
							$(".refuse").mzDialog();
						},
						onLoadError : function() {
							$.loading.end();
						}

					});

	function queryParams(params) {
		var obj = {
			pageSize : params.limit,
			currentPage : params.offset / params.limit + 1
		}
		// 显示加载框
		$.loading.start();
		return obj;
	};

	function responseHandler(res) {
		if (res.code === "200" && res.data != null) {
			return {
				"rows" : res.list,
				"total" : res.data.total
			};
		} else {
			return {
				"rows" : [],
				"total" : 0
			};
		}
	}
	// 查询
	$("#query").on("click", function() {
		$('#querylist').bootstrapTable('refreshOptions', {

			pageNumber : 1,
			pageSize : 10
		});
	});
	// 新增
	
	 $("#add").on("click",function(){ window.location.href = "/attendance-mgr/pages/detail/out_detail_info.html" ; });
	// 重置
	$("#resets").on("click", function() {
		$("#goods_info_form")[0].reset();
		$("#query").click();
	});

	window.operateEvents = {

		'click .delete' : function(e, value, row, index) {
			bootbox.confirm("请确要删除"+row.employeeName+"员工刷卡记录吗？", function(result) {
				if (result) {
					deleteOutDetailById(row.id);
				}
			});
		}
	}
});

// 删除充值项信息


