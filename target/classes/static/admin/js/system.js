$(function () {
    let confirm = $('#confirm')
    let cancel = $('#cancel')
    let update = $("#update")
    let insert = $('#insert')
    let close = $('#close')
    let all = $('#all')
    let method = "post"

    update.click(function () {
        method = 'put'
    })
    insert.click(function () {
        method = 'post'
    })
    close.click(function () {
        update.attr('data-toggle','')
        $('#adminName').val('')
        $('#adminPassword').val('')
        $('#adminRole').val('')
    })
    cancel.click(function () {
        update.attr('data-toggle','')
        $('#adminName').val('')
        $('#adminPassword').val('')
        $('#adminRole').val('')
    })

    //全选或取消
    all.click(function () {
        $('input[name="checkbox"]').prop('checked',$('#all').prop('checked'))
    })

    //更新
    update.click(function () {
        let selected = getSelected($('input[name="checkbox"]'))
        if (selected.length != 1){
            alert("请选择一个要修改的用户")
        }else {
            let user = $('#'+selected[0])
            $('#adminName').val(user.attr('username'))
            $('#adminPassword').val(user.attr('password'))
            $('#adminRole').val(user.attr('role'))
            $(this).attr('data-toggle','modal')
        }
    })

    //删除
    $('#delete').click(function () {
        let selected = getSelected($('input[name="checkbox"]'))
        if (selected.length == 0){
           myalert("提示框","请选择要删除的用户")
        }else {
            $.ajax({
                url: realPath + "/admin/user",
                type: 'delete',
                data:{
                    ids: selected
                },
                success:function (result) {
                    if (result.code == 200){
                        myalert("提示框",result.message,function () {
                            location.reload()
                        })
                    }else {
                        myalert("提示框",result.message)
                    }
                }
            })
        }
    })

    confirm.click(function () {
        if ($('#adminName').val().trim()!="" && $('#adminPassword').val().trim()!=""){
            let formData = new FormData($('#adminInfo')[0])
            if (method == 'put'){
                formData.append('id',getSelected($('input[name="checkbox"]'))[0])
            }
            $.ajax({
                url: realPath + "/admin/user",
                type: method,
                contentType:false,
                processData:false,
                data: formData,
                success:function (result) {
                    if (result.code==200){
                        myalert("提示框",result.message,function () {
                            confirm.attr('data-dismiss','modal')
                            location.reload()
                        })
                    }else {
                        myalert("提示框",result.message)
                    }
                }
            })
        }else{
            myalert("提示框","用户名或密码不能为空")
        }
    })
})