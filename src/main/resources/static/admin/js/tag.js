$(function (){
    let tags = $('#tags')
    let updateTag = $('#updateTag')
    let confirm = $('#confirm')
    let cancel = $('#cancel')
    let submit = $('#submit')

    //模态框tagsInput
    tags.tagsInput({
        width: '100%',
        placeholderColor: '#058c27',
        defaultText:'输入标签'
    })

    //全选或取消
    updateTag.on('click','#all',function () {
        $('input[name="checkbox"]').prop('checked',$('#all').prop('checked'))
    })

    //提交
    confirm.click(function () {
        if (tags.val().trim().length>0){
            let names = tags.val().split(",")
            names.map(e=>e.trim())
            $.ajax({
                url: realPath + "/admin/tag",
                type: "post",
                data: {
                    names
                },
                success:function (result) {
                    if (result.code==200){
                        myalert("提示框",result.message,function () {
                            $('#updateTag').load(realPath + '/admin/tag/list?pageSize='+$('#pageSize').val()+'&search='+$('#search').val())
                        })
                    }
                    else{
                        myalert("提示框",result.message)
                    }
                }
            })
        }else {
            myalert("提示框","请输入要添加的标签名")
        }
    })

    //返回
    cancel.click(function () {
        updateTag.load(realPath+"/admin/tag/list?pageSize="+$('#pageSize').val()+"&search="+$('#search').val())
    })

    //搜索
    $("#submit").click(function () {
        $('#updateTag').load(realPath+'/admin/tag/list?search='+$('#search').val()+'&pageSize='+$('#pageSize').val())
    })

    //删除
    $(".operation").on('click','#delete',function (){
        let selected = getSelected($('input[name="checkbox"]'))
        if (selected.length>0){
            $.ajax({
                url: realPath+"/admin/tag",
                type: 'delete' ,
                data:{
                    'ids':selected
                },
                success:function (result) {
                    if (result.code==200){
                        myalert("提示框",result.message,function () {
                            $('#updateTag').load(realPath + '/admin/tag/list?pageSize='+$('#pageSize').val()+'&search='+$('#search').val())
                        })
                    }else {
                        myalert("提示框",result.message)
                    }
                }
            })
        }else {
            myalert("提示框","请选择要删除的标签")
        }
    })

    //每页记录数
    updateTag.on('change','#pageSize',function (e) {
        updateTag.load(realPath + '/admin/tag/list?pageSize='+$(this).val()+'&search='+$('#search').val())
    })

    //翻页跳转
    updateTag.on('click','.page a',function (e) {
        e.preventDefault()
        updateTag.load(localhostPath+$(this).attr('href'))
    })

    //搜索
    submit.click(function () {
        updateTag.load(realPath+'/admin/tag/list?search='+$('#search').val()+'&pageSize='+$('#pageSize').val())
    })
})