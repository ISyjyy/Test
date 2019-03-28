

jQuery(function ($) {
    $('#mytable').bootstrapTable({
        columns:[
            {
                field : '',
                title : '序号',
                width : '5%',
                formatter : function(value, row, index) {
                    /*
                     * var cPage = row.currentPage; var size =
                     * row.pageSize;
                     */
                    return index + 1;// +(cPage-1)*size
                }
            },
              {
             field: 'username',
            title: '用户名',
              width: '45%'
    },
            {
                field: 'password',
                title: '密码',
                width: '50%'
            }
],
        method: 'post',
        striped: true,
        dataType: "json",
        pagination: true,
        queryParamsType : "limit",
        singleSelect: false,
        contentType: "application/json",
        pageSize : 3,
        pageNumber : 1,
        pageList : [10,20,50],
        search: false, //不显示 搜索框
        showColumns: false, //不显示下拉框（选择显示的列）
        escape: true,        //字符转义
        sidePagination: "server", //服务端请求
        queryParams : queryParams,
        responseHandler: responseHandler,
        url: "queryUser.do",
        //数据显示完毕后事件
        onLoadSuccess: function (data) {
            //隐藏加载框
            $.loading.end();
            //初始化窗口函数
            $(".save").mzDialog();
            $(".refuse").mzDialog();
        },
        onLoadError: function () {
            $.loading.end();
        }
    });
    function queryParams(params) {
        var obj = {
            pageSize: params.limit,
            currentPage: params.offset / params.limit + 1 ,
        }
        //显示加载框
        $.loading.start();
        return obj;
    };
    function responseHandler(res) {
        if (res.data!=null) {
            return {
                "rows": res.data.list,
                "total": res.data.total
            };
        } else {
            return {
                "rows": [],
                "total": 0
            };
        }
    }

    });