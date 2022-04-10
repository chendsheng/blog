$(function () {

    let select = $('#select')
    let show = $('#show>li')
    let insert = $('#insert')
    let update = $('#update')
    let confirm = $('#confirm')
    let cancel = $('#cancel')
    let updateCategory = $('#updateCategory')

    //全选或取消
    $('#updateCategory').on('click','#all',function () {
        let checkbox = $('input[name="checkbox"]')
        checkbox.prop('checked',$('#all').prop('checked'))
    })

    //新增
    insert.click(function () {
        show.eq(/\d+/.exec(select.val())- 1).addClass('selected')
        $('.modal-title').html('分类新增')
    })

    //修改
    update.click(function () {
        let checkbox = $('input[name="checkbox"]')
        let selected = getSelected(checkbox)
        if(selected.length == 1) {
            update.attr('data-toggle','modal')
            $('#categoryName').val($("input[id="+selected[0]+"]")
                .parent().next().text().trim())

            let src = $("input[id="+selected[0]+']').parent().siblings().children('img').attr('src')
            // console.log(src)
            // "/dds/admin/img/category/14.png"
            //去除项目路径 "/admin/img/category/14.png"
            select.val(src.substr(src.indexOf('/admin')))
            show.eq(/\d+/.exec(select.val())- 1).addClass('selected').siblings().removeClass('selected')
            $('.modal-title').html('分类修改')
        }else {
            update.removeAttr('data-toggle')
            myalert("提示框","请选择一个要修改的分类")
        }
    })

    //提交
    confirm.click(function () {
        let id = getSelected($('input[name="checkbox"]'))
        if ($('#categoryName').val().trim()==""){
            myalert("提示框","请输入分类名称")
        }
        else {
            if (id.length == 1){
                //修改
                $.ajax({
                    url: realPath + '/admin/category/'+ id[0],
                    type:'put',
                    data: $('#categoryInfo').serialize(),
                    success: function (result) {
                        if (result.code==200){
                            myalert("提示框",result.message,function () {
                                updateCategory.load(realPath + '/admin/category/list?pageSize='+$('#pageSize').val()+'&search='+$('#search').val())
                            })
                        }else {
                            myalert("提示框",result.message)
                        }
                    }
                })
            }else{
                //新增
                $.ajax({
                    url: realPath + '/admin/category',
                    type:'post',
                    data: $('#categoryInfo').serialize(),
                    success: function (result) {
                        if (result.code==200){
                            myalert("提示框",result.message,function () {
                                updateCategory.load(realPath + '/admin/category/list?pageSize='+$('#pageSize').val()+'&search='+$('#search').val())
                            })
                        }else {
                            myalert("提示框",result.message)
                        }
                    }
                })
            }
        }
    })

    //删除
    $('#delete').click(function () {
        let selected = getSelected($('input[name="checkbox"]'))
        if(selected.length > 0 ) {
            $.ajax({
                url: realPath+"/admin/category",
                type: 'delete',
                data: {
                    ids:selected
                },
                success: function (result) {
                    if (result.code==200){
                        myalert("提示框",result.message,function () {
                            updateCategory.load(realPath + '/admin/category/list?pageSize='+$('#pageSize').val()+'&search='+$('#search').val())
                        })
                    }else{
                        myalert("提示框",result.message)
                    }
                }
            })
        } else{
            myalert("提示框","请选择要删除的分类")
        }
    })

    //返回
    cancel.click(function () {
        updateCategory.load(realPath+"/admin/category/list?pageSize="+$('#pageSize').val()+"&search="+$('#search').val())
    })

    //模态框
    select.change(function () {
        show.eq(/\d+/.exec(select.val())- 1).addClass('selected').siblings().removeClass('selected')
    })
    $('#show>li').click(function () {
        select.val("/admin/img/category/"+($(this).index()+1)+".png")
        $(this).addClass('selected').siblings().removeClass('selected')
    })

    //翻页跳转
    updateCategory.on('change','#pageSize',function (e) {
        updateCategory.load(realPath + '/admin/category/list?pageSize='+$(this).val()+'&search='+$('#search').val())
    })

    updateCategory.on('click','.page a',function (e) {
        e.preventDefault()
        updateCategory.load(localhostPath+$(this).attr('href'))
    })

    //搜索
    $("#submit").click(function () {
        $('#updateCategory').load(realPath+'/admin/category/list?search='+$('#search').val()+'&pageSize='+$('#pageSize').val())
    })
})